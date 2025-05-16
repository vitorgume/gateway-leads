package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import org.springframework.stereotype.Component;

@Component
public class MensagemBoasVindas implements MensagemType{


    @Override
    public String getMensagem(String nomeVendedor) {
        return "Olá! Muito obrigado pelo interesse em conversar com a Neoprint, será um prazer ajudá-la(o)!";
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.BOAS_VINDAS.getCodigo();
    }
}
