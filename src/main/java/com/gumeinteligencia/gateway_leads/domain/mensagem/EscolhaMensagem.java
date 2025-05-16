package com.gumeinteligencia.gateway_leads.domain.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EscolhaMensagem {
    ESCOLHA_ENCERRAMENTO(0, "Encerramento"),
    ESCOLHA_FINANCEIRO(1, "Financeiro"),
    ESCOLHA_COMERCIAL(2, "Comercial");

    private final Integer codigo;
    private final String descricao;
}
