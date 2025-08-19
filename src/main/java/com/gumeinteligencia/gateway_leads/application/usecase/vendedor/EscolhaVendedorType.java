package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;

public interface EscolhaVendedorType {
    EscolhaVendedor escolher();
    boolean deveAplicar(Regiao regiao, Segmento segmento);
}
