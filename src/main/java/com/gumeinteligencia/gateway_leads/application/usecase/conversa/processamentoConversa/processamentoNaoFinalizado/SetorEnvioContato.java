package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SetorEnvioContato {

    FINANCEIRO(0, "Financeiro"),
    LOGISTICA(1, "Logistica");

    private final int codigo;
    private final String descricao;
}
