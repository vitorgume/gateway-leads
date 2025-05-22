package com.gumeinteligencia.gateway_leads.domain.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EscolhaMensagem {
    ESCOLHA_ENCERRAMENTO(0, "Encerramento"),
    ESCOLHA_COMERCIAL(1, "Comercial"),
    ESCOLHA_FINANCEIRO(2, "Financeiro"),
    ESCOLHA_LOGISTICA(3, "Logística");

    private final Integer codigo;
    private final String descricao;
}
