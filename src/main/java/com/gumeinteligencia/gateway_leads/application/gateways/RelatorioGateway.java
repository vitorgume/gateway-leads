package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioOnlineDto;

public interface RelatorioGateway {
    void atualizarRelatorioOnline(RelatorioOnlineDto novaLinha);
}
