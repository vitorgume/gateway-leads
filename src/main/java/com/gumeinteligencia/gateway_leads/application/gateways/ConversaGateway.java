package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Conversa;

import java.util.Optional;

public interface ConversaGateway {
    Optional<Conversa> consultarPorCliente(Cliente cliente);

    void salvar(Conversa novaConversa);
}
