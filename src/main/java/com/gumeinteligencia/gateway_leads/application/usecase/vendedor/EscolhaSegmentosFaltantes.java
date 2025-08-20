package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(4)
public class EscolhaSegmentosFaltantes implements EscolhaVendedorType {

    private final List<Segmento> segmentos = new ArrayList<>(List.of(
            Segmento.BOUTIQUE_LOJAS,
            Segmento.ALIMENTOS,
            Segmento.OUTROS
    ));

    @Override
    public EscolhaVendedor escolher() {
        return EscolhaVendedor.builder()
                .vendedor("Nilza")
                .roleta(true)
                .build();
    }

    @Override
    public boolean deveAplicar(Regiao regiao, Segmento segmento) {
        return segmentos.contains(segmento);
    }
}
