package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EscolhaVendedorFactory {

    private final List<EscolhaVendedorType> escolhas;

    public EscolhaVendedorType escolha(Segmento segmento, Regiao regiao) {
        return escolhas.stream()
                .filter(escolha -> escolha.deveAplicar(regiao, segmento))
                .findFirst()
                .orElseThrow(EscolhaNaoIdentificadoException::new);
    }

}
