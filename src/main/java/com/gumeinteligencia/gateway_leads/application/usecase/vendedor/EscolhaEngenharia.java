package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class EscolhaEngenharia implements EscolhaVendedorType {

    @Override
    public EscolhaVendedor escolher() {
        return EscolhaVendedor.builder()
                .roleta(true)
                .vendedor(null)
                .build();
    }

    @Override
    public boolean deveAplicar(Regiao regiao, Segmento segmento) {
        return segmento.getCodigo() == 3;
    }
}
