package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarOutroContatoComercial implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor) {
        return "Identifiquei que você já estava em conversa com o(a) " + nomeVendedor + ", vou repessar você novamente " +
                "para o vendedor.";
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_OUTRO_CONTATO_COMERCIAL.getCodigo();
    }
}
