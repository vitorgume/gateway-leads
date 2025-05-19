package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class MensagemBoasVindas implements MensagemType{


    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Olá! Muito obrigado pelo interesse em conversar com a Neoprint, será um prazer ajudá-la(o)!";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.BOAS_VINDAS.getCodigo();
    }
}
