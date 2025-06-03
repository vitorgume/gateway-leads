package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;

import java.util.List;
import java.util.Optional;

public interface OutroContatoGateway {
    Optional<OutroContato> consultarPorNome(String nome);

    List<OutroContato> listar();
}
