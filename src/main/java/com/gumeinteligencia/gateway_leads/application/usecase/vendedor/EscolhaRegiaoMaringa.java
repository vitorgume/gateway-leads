package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import jakarta.annotation.Nullable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class EscolhaRegiaoMaringa implements EscolhaVendedorType {
    @Override
    public EscolhaVendedor escolher() {
        return EscolhaVendedor.builder()
                .roleta(false)
                .vendedor("Samara")
                .build();
    }

    @Override
    public boolean deveAplicar(@Nullable Regiao regiao, @Nullable Segmento segmento) {
        if(regiao != null)
            return regiao.getCodigo() == 2;
        else
            return false;
    }
}
