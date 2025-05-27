package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoConversaExistenteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoNovaConversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessarMensagemUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ProcessamentoConversaExistenteUseCase processamentoConversaExistenteUseCase;
    private final ProcessamentoNovaConversa processamentoNovaConversa;

    public void processarNovaMensagem(Mensagem mensagem) {
        log.info("Processando nova mensagem. Mensagem: {}", mensagem);

        clienteUseCase
                .consultarPorTelefone(mensagem.getTelefone())
                .ifPresentOrElse(
                        cliente -> processamentoConversaExistenteUseCase.processarConversaExistente(cliente, mensagem),
                        () -> processamentoNovaConversa.iniciarNovaConversa(mensagem)
                );

        log.info("Mensagem processada com sucesso.");
    }
}
