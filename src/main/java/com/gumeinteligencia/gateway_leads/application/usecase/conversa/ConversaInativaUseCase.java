package com.gumeinteligencia.gateway_leads.application.usecase.conversa;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.application.usecase.vendedor.VendedorUseCase;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ConversaInativaUseCase {

    private final ConversaUseCase conversaUseCase;
    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Value("${spring.profiles.active}")
    private final String profile;

    public ConversaInativaUseCase(
            ConversaUseCase conversaUseCase,
            VendedorUseCase vendedorUseCase,
            MensagemUseCase mensagemUseCase,
            MensagemBuilder mensagemBuilder,
            @Value("${spring.profiles.active}") String profile
    ) {
        this.conversaUseCase = conversaUseCase;
        this.vendedorUseCase = vendedorUseCase;
        this.mensagemUseCase = mensagemUseCase;
        this.mensagemBuilder = mensagemBuilder;
        this.profile = profile;
    }

    @Scheduled(cron = "${neoprint.cron.conversa.inativa}")
    public void verificaAusenciaDeMensagem() {
        List<Conversa> conversas = conversaUseCase.listarNaoFinalizados();
        log.info("Verificando se existe alguma mensagem inativa por mais de 30 minutos. Conversas: {}", conversas);
        

        LocalDateTime agora = LocalDateTime.now();


        List<Conversa> conversasAtrasadas = conversas.stream()
                .filter(conversa -> {
                            if(conversa.getUltimaMensagem() != null)
                                return profile.equals("dev")
                                        ? conversa.getUltimaMensagem().plusMinutes(30).isBefore(agora)
                                        : conversa.getUltimaMensagem().plusSeconds(30).isBefore(agora);

                            return false;
                        }
                )
                .toList();


        if(!conversasAtrasadas.isEmpty()) {
            conversasAtrasadas.forEach(conversa -> {
                conversa.setFinalizada(true);
                Vendedor vendedor = vendedorUseCase.roletaVendedoresConversaInativa(conversa.getCliente());
                conversa.setVendedor(vendedor);
                conversa.setInativa(true);
                mensagemUseCase
                        .enviarContatoVendedor(
                                vendedor,
                                conversa.getCliente()
                        );
                mensagemUseCase.enviarMensagemVendedor(mensagemBuilder.getMensagem(TipoMensagem.CONTATO_INATIVO, null, null), vendedor.getTelefone(), conversa);
                conversaUseCase.salvar(conversa);
            });
        }

        log.info("Verificação concluida com sucesso.");
    }
}
