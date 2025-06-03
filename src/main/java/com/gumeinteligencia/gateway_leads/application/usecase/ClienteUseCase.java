package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.ClienteJaCadastradoException;
import com.gumeinteligencia.gateway_leads.application.exceptions.ClienteNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.ClienteGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void inativar(UUID id) {
        Cliente cliente = this.consultarPorId(id);

        cliente.setInativo(true);

        gateway.salvar(cliente);
    }

    private Cliente consultarPorId(UUID id) {
        Optional<Cliente> cliente = gateway.consultarPorId(id);

        if(cliente.isEmpty()) {
            throw new ClienteNaoEncontradoException();
        }

        return cliente.get();
    }

    public List<RelatorioContatoDto> getRelatorio() {
        return gateway.getRelatorioContato();
    }

    public List<RelatorioContatoDto> getRelatorioSegundaFeira() {
        return gateway.getRelatorioContatoSegundaFeira();
    }
}
