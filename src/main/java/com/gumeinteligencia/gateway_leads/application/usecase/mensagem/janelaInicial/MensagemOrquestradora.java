package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MensagemOrquestradora {

    private final JanelaInicialDeBloqueio janelaInicial;

    public void enviarComEspera(String telefone, List<String> mensagens, Mensagem ultima) {
        janelaInicial.adicionarSeNaoExiste(telefone);
        janelaInicial.armazenarMensagens(telefone, mensagens, ultima);
    }
}
