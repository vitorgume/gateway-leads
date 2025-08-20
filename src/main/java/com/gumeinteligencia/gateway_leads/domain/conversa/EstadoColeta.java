package com.gumeinteligencia.gateway_leads.domain.conversa;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoColeta {
    COLETA_SEGMENTO(0, "Coleta Segmento"),
    COLETA_REGIAO(1, "Coleta região"),
    FINALIZA_COLETA(2, "Finaliza coleta");

    private final int codigo;
    private final String descricao;
}
