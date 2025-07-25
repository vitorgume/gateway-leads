package com.gumeinteligencia.gateway_leads.application.usecase.conversa;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.VendedorUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class ConversaInativaUseCaseDev {
    private final ConversaUseCase conversaUseCase;
    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Scheduled(cron = "0 * * * * *")
    public void verificaAusenciaDeMensagem() {
        List<Conversa> conversas = conversaUseCase.listarNaoFinalizados();
        log.info("Verificando se existe alguma mensagem inativa por mais de 30 minutos. Conversas: {}", conversas);


        LocalDateTime agora = LocalDateTime.now();

        List<Conversa> conversasAtrasadas = conversas.stream()
                .filter(conversa -> {
                            if(conversa.getUltimaMensagem() != null)
                                return conversa.getUltimaMensagem().plusSeconds(30).isBefore(agora);

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
