package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;

public interface MensagemType {
    String getMensagem(String nomeVendedor, Cliente cliente);
    Integer getTipoMensagem();
}
