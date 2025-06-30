package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoInativo;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;

public interface ProcessamentoConversaInativa {
    void processar(Cliente cliente, Conversa conversa);
    String mensagem();
}
