package com.gumeinteligencia.gateway_leads.application.usecase.conversa;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.VendedorUseCase;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversaInativaUseCase {

    private final ConversaUseCase conversaUseCase;
    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;

    @Scheduled(cron = "0 * * * * *")
    public void verificaAusenciaDeMensagem() {
        log.info("Verificando se existe alguma mensagem inativa por mais de 10 minutos");
        List<Conversa> conversas = conversaUseCase.listarNaoFinalizados();

        LocalDateTime agora = LocalDateTime.now();

        List<Conversa> conversasAtrasadas = conversas.stream()
                .filter(conversa -> {
                            if(conversa.getUltimaMensagem() != null)
                                return conversa.getUltimaMensagem().plusMinutes(10).isBefore(agora);

                            return false;
                        }
                )
                .toList();


        if(!conversasAtrasadas.isEmpty()) {
            conversasAtrasadas.forEach(conversa -> {
                conversa.setFinalizada(true);
                conversaUseCase.salvar(conversa);
                Vendedor vendedor = vendedorUseCase.consultarVendedor("Mariana");
                mensagemUseCase
                        .enviarContatoVendedor(
                                vendedor,
                                conversa.getCliente(),
                                "Contato inativo por mais de 10 minutos"
                        );
            });
        }

        log.info("Verificação concluida com sucesso.");
    }
}
