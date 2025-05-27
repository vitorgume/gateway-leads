package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;

import java.util.List;
import java.util.Optional;

public interface VendedorGateway {
    Optional<Vendedor> consultarVendedor(String nilza);

    List<Vendedor> listar();

    List<Vendedor> listarSemNilza();

    Vendedor salvar(Vendedor vendedor);

    void deletar(Long id);

    Optional<Vendedor> consultarPorTelefone(String telefone);
}
