package com.gumeinteligencia.gateway_leads.domain.outroContato;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Setor {

    FINANCEIRO(0, "Financeiro"),
    LOGISTICA(1, "Logistica"),
    GERENTE(2, "Gerente"),
    OUTRO(3, "Outro");

    private final int codigo;
    private final String descricao;
}
