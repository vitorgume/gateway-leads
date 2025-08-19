package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Order(1)
public class EscolhaMedicina implements EscolhaVendedorType {

    private static String ultimoVendedorMedicina;
    private final Random random = new Random();

    @Override
    public EscolhaVendedor escolher() {

        List<String> vendedoresMedicina = List.of("Cinthya", "Marcia");
        String vendedorEscolhido;
        do {
            vendedorEscolhido = vendedoresMedicina.get(random.nextInt(vendedoresMedicina.size()));
        } while (vendedorEscolhido.equals(ultimoVendedorMedicina));

        ultimoVendedorMedicina = vendedorEscolhido;

        return EscolhaVendedor.builder()
                .roleta(false)
                .vendedor(vendedorEscolhido)
                .build();
    }

    @Override
    public boolean deveAplicar(Regiao regiao, Segmento segmento) {
        return segmento.getCodigo() == 1;
    }
}
