package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.ClienteJaCadastradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.ClienteGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    public Cliente salvar(Cliente cliente) {
        return gateway.salvar(cliente);
    }

    public void deletar(UUID id) {
        gateway.deletar(id);
    }

    public void deletarPorTelefone(String telefone) {
        gateway.deletarPorTelefone(telefone);
    }
}
