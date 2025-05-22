package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SetorEnvioContato {

    FINANCEIRO(0),
    LOGISTICA(1);

    private final int codigo;
}
