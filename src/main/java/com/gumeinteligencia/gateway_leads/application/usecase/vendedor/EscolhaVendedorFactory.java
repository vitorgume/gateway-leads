package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EscolhaVendedorFactory {

    private final List<EscolhaVendedorType> strategies;

    public EscolhaVendedorType escolha(Segmento segmento, Regiao regiao) {
        return strategies.stream()
                .filter(s -> safeDeveAplicar(s, segmento, regiao))
                .findFirst()
                .orElseThrow(EscolhaNaoIdentificadoException::new);

    }

    private boolean safeDeveAplicar(EscolhaVendedorType s, Segmento segmento, Regiao regiao) {
        try {
            return s.deveAplicar(regiao, segmento);
        } catch (Exception e) {
            log.warn("deveAplicar falhou em {} (segmento={}, regiao={})",
                    s.getClass().getSimpleName(), segmento, regiao, e);
            return false;
        }
    }

}
