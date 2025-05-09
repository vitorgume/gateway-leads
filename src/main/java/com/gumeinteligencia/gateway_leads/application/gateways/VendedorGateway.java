package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Vendedor;

import java.util.List;
import java.util.Optional;

public interface VendedorGateway {
    Optional<Vendedor> consultarVendedor(String nilza);

    List<Vendedor> listar();

    List<Vendedor> listarSemNilza();

    Vendedor salvar(Vendedor vendedor);
}
