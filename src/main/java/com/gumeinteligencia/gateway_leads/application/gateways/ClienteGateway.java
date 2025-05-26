package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteGateway {
    Optional<Cliente> consutlarPorTelfone(String telefone);

    Cliente salvar(Cliente novoCliente);

    void deletar(UUID id);

    Optional<Cliente> consultarPorId(UUID id);

    List<RelatorioContatoDto> getRelatorioContato(Long id);
}
