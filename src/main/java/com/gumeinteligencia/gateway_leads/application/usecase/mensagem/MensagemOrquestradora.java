package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MensagemOrquestradora {

    private final MensagemUseCase mensagemUseCase;
    private final ProcessarMensagemUseCase processarMensagemUseCase;

    public void enviarComEspera(String telefone, List<String> mensagens, Conversa conversa, Mensagem ultimaRecebida) {

    }
}
