package com.gumeinteligencia.gateway_leads.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Segmento {
    SAUDE(0, "saude"),
    ARQUITETURA(1, "arquitetura"),
    ENGENHARIA(2, "engenharia"),
    VAREJO(3, "varejo"),
    INDUSTRIA(4, "industria"),
    ALIMENTOS(5, "alimentos"),
    CELULAR(6, "celular"),
    OUTROS(7, "outros");

    private final int codigo;
    private final String descricao;
}
