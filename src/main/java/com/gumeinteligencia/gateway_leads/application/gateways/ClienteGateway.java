package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClienteGateway {

    Optional<Cliente> consutlarPorTelfone(String telefone);

    Cliente salvar(Cliente novoCliente);

    void deletar(UUID id);

    Optional<Cliente> consultarPorId(UUID id);
}
