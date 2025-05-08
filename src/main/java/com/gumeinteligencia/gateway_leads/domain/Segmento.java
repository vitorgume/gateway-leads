package com.gumeinteligencia.gateway_leads.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Segmento {
    SAUDE(0),
    ARQUITETURA(1),
    ENGENHARIA(2),
    VAREJO(3),
    INDUS(4),
    ALIMENTOS(5),
    OUTROS(6);

    private final int codigo;
}
