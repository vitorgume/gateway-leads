package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import jakarta.annotation.Nullable;

public interface EscolhaVendedorType {
    EscolhaVendedor escolher();
    boolean deveAplicar(@Nullable Regiao regiao, @Nullable Segmento segmento);
}
