package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.CardDto;

import java.util.Optional;

public interface CrmGateway {
    void atualizarCard(CardDto cardDto, Integer idLead);

    Optional<Integer> consultaLeadPeloTelefone(String telefone);
}
