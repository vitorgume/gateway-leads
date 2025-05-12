package com.gumeinteligencia.gateway_leads.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Regiao {
    MARINGA(0, "Maringá"),
    REGIAO_MARINGA(1, "Região de Maringá"),
    OUTRA(2, "Outras regiões");

    private final int codigo;
    private final String descricao;

}
