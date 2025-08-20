package com.gumeinteligencia.gateway_leads.domain.conversa;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
public enum     MensagemDirecionamento {
    MENSAGEM_INICIAL(0, "Mensagem inicial"),
    ESCOLHA_FINANCEIRO(1, "Escolha financeiro"),
    ESCOLHA_COMERCIAL(2, "Escolha comercial"),
    ESCOLHA_COMERCIAL_RECONTATO(3, "Escolha comercial recontato"),
    COLETA_NOME(4, "Coleta nome"),
    ESCOLHA_LOGISTICA(5, "Escolha logística");

    private final int codigo;
    private final String descricao;
}
