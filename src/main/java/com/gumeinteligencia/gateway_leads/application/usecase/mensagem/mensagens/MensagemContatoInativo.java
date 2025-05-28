package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemContatoInativo implements MensagemType{

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Contato inativo por mais de 10 minutos";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.CONTATO_INATIVO.getCodigo();
    }
}
