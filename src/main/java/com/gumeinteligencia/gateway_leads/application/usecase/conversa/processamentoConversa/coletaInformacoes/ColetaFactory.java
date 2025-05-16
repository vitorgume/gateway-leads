package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ColetaFactory {

    private final List<ColetaType> coletas;

    public ColetaType create(MensagemColeta estado) {
        return coletas.stream()
                .filter(coleta -> coleta.deveAplicar(estado))
                .findFirst()
                .orElseThrow(EscolhaNaoIdentificadoException::new);
    }
}
