package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.ClienteJaCadastradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.ClienteGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteUseCase {
    
    private final ClienteGateway gateway;

    public Optional<Cliente> consultarPorTelefone(String telefone) {
        return gateway.consutlarPorTelfone(telefone);
    }

    public Cliente cadastrar(Cliente novoCliente) {
        Optional<Cliente> clienteOptional = this.consultarPorTelefone(novoCliente.getTelefone());

        clienteOptional.ifPresent(cliente -> {throw new ClienteJaCadastradoException();});

        return gateway.salvar(novoCliente);
    }

    public void salvar(Cliente cliente) {
        gateway.salvar(cliente);
    }
}
