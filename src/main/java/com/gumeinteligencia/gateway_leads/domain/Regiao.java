package com.gumeinteligencia.gateway_leads.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Regiao {
    MARINGA(1, "Maringá"),
    REGIAO_MARINGA(2, "Região de Maringá"),
    OUTRA(3, "Outras regiões");

    private final int codigo;
    private final String descricao;

}
