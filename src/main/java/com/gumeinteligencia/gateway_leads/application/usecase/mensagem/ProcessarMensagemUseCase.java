package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.conversaExistente.ConversaExistente;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.conversaExistente.ProcessamentoConversaExistenteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoNovaConversa;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.validatorMensagens.ValidadorMensagemComposite;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessarMensagemUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaExistente conversaExistente;
    private final ProcessamentoNovaConversa processamentoNovaConversa;
    private final ValidadorMensagemComposite validadorMensagem;


    public void processarNovaMensagem(Mensagem mensagem) {
        log.info("Processando nova mensagem. Mensagem: {}", mensagem);

        if (validadorMensagem.deveIgnorar(mensagem)) {
            log.info("Mensagem ignorada. Motivo: Validação.");
            return;
        }

        clienteUseCase
                .consultarPorTelefone(mensagem.getTelefone())
                .ifPresentOrElse(
                        cliente -> conversaExistente.processarConversaExistente(cliente, mensagem),
                        () -> processamentoNovaConversa.iniciarNovaConversa(mensagem)
                );

        log.info("Mensagem processada com sucesso.");
    }
}
