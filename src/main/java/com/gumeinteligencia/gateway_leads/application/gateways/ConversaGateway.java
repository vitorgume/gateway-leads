package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;

import java.util.List;
import java.util.Optional;

public interface ConversaGateway {
    Optional<Conversa> consultarPorCliente(Cliente cliente);

    Conversa salvar(Conversa novaConversa);

    List<Conversa> listarNaoFinalizados();
}
