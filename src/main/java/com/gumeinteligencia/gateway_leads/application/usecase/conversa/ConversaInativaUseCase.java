package com.gumeinteligencia.gateway_leads.application.usecase.conversa;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.CrmUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.application.usecase.vendedor.VendedorUseCase;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.StatusConversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ConversaInativaUseCase {

    private final ConversaUseCase conversaUseCase;
    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;
    private final CrmUseCase crmUseCase;

    @Value("${spring.profiles.active}")
    private final String profile;

    public ConversaInativaUseCase(
            ConversaUseCase conversaUseCase,
            VendedorUseCase vendedorUseCase,
            MensagemUseCase mensagemUseCase,
            MensagemBuilder mensagemBuilder,
            CrmUseCase crmUseCase,
            @Value("${spring.profiles.active}") String profile
    ) {
        this.conversaUseCase = conversaUseCase;
        this.vendedorUseCase = vendedorUseCase;
        this.mensagemUseCase = mensagemUseCase;
        this.mensagemBuilder = mensagemBuilder;
        this.profile = profile;
        this.crmUseCase = crmUseCase;
    }

    @Scheduled(cron = "${neoprint.cron.conversa.inativa}")
    public void verificaAusenciaDeMensagem() {
        List<Conversa> conversas = conversaUseCase.listarNaoFinalizados();
        log.info("Verificando se existe alguma mensagem inativa por mais do tempo determinado. Conversas: {}", conversas);
        

        LocalDateTime agora = LocalDateTime.now();


        List<Conversa> conversasAtrasadas = conversas.stream()
                .filter(conversa -> {
                            if(conversa.getUltimaMensagem() != null) {
                                if (conversa.getStatus().getCodigo().equals(3)) {
                                    return profile.equals("prod")
                                            ? conversa.getUltimaMensagem().plusHours(1).plusMinutes(30).isBefore(agora)
                                            : conversa.getUltimaMensagem().plusSeconds(10).isBefore(agora);
                                } else {
                                    return profile.equals("prod")
                                            ? conversa.getUltimaMensagem().plusHours(12).isBefore(agora)
                                            : conversa.getUltimaMensagem().plusSeconds(20).isBefore(agora);
                                }
                            }

                            return false;
                        }
                )
                .toList();

        log.info("Conversas atrasadas: {}", conversasAtrasadas);


        if (!conversasAtrasadas.isEmpty()) {
            conversasAtrasadas.forEach(conversa -> {
                try {
                    log.info("Processando conversa atrasada {}", conversa);
                    processarConversaAtrasada(conversa);
                    log.info("Processamento concluido com sucesso");
                } catch (Exception e) {
                    log.error("Erro ao processar conversa atrasada {}: {}", conversa.getId(), e.getMessage(), e);
                }
            });
        }

        log.info("Verificação concluida com sucesso.");
    }

    public void processarConversaAtrasada(Conversa conversa) {
        log.info("Processando conversa atrasada. Conversa: {}", conversa);
        boolean vaiParaG1 = !conversa.getFinalizada() && !conversa.getStatus().getCodigo().equals(0);

        if (vaiParaG1) {
            atualizarStatusConversa(conversa, StatusConversa.INATIVO_G1, false, null);
            mensagemUseCase.enviarMensagem(
                    mensagemBuilder.getMensagem(TipoMensagem.RECONTATO_INATIVO_G1, null, null),
                    conversa.getCliente().getTelefone(),
                    conversa
            );

        } else {
            Vendedor vendedor = vendedorUseCase.roletaVendedoresConversaInativa(conversa.getCliente());
            atualizarStatusConversa(conversa, StatusConversa.INATIVO_G2, true, vendedor);
            crmUseCase.atualizarCrm(vendedor, conversa.getCliente(), conversa);
        }
        ;
    }

    @Transactional
    public void atualizarStatusConversa(Conversa conversa, StatusConversa novoStatus, boolean finalizada, Vendedor vendedorOuNull) {
        conversa.setStatus(novoStatus);
        conversa.setFinalizada(finalizada);

        if (vendedorOuNull != null) {
            conversa.setVendedor(vendedorOuNull);
        }

        conversaUseCase.salvar(conversa);
    }

}
