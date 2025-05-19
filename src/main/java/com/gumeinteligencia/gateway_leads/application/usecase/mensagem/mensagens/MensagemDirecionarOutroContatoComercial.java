package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarOutroContatoComercial implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Identifiquei que você já estava em conversa com o(a) " + nomeVendedor + ", vou repessar você novamente " +
                "para o vendedor.";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_OUTRO_CONTATO_COMERCIAL.getCodigo();
    }
}
