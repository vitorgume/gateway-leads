package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Cliente;

import java.util.Optional;

public interface ClienteGateway {
    Optional<Cliente> consutlarPorTelfone(String telefone);

    Cliente salvar(Cliente novoCliente);
}
