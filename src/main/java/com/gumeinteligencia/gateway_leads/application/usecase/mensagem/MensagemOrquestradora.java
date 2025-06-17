package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MensagemOrquestradora {

    private final JanelaInicialDeBloqueio janelaInicialDeBloqueio;

    public void enviarComEspera(String telefone, List<String> mensagens, Mensagem ultima) {
        janelaInicialDeBloqueio.adicionarSeNaoExiste(telefone);
        janelaInicialDeBloqueio.armazenarMensagens(telefone, mensagens, ultima);
    }
}
