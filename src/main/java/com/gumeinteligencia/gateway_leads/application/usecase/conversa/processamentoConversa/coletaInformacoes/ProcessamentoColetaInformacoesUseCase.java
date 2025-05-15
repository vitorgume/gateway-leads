package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import org.springframework.stereotype.Service;

@Service
public class ProcessamentoColetaInformacoesUseCase {

    private ColetaType coletaType;

    public ColetaType setColetaType(ColetaType coletaType) {
        this.coletaType = coletaType;
        return this.coletaType;
    }


}
