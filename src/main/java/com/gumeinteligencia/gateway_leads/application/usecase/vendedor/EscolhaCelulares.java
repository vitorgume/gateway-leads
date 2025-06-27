package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import org.springframework.stereotype.Component;

@Component
public class EscolhaCelulares implements EscolhaVendedorType {

    @Override
    public EscolhaVendedor escolher() {
        return EscolhaVendedor.builder()
                .roleta(false)
                .vendedor("Mariana")
                .build();
    }

    @Override
    public boolean deveAplicar(Regiao regiao, Segmento segmento) {
        return segmento.getCodigo() == 5;
    }
}
