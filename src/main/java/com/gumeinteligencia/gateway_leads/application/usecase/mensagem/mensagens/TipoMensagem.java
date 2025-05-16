package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoMensagem {
    BOAS_VINDAS(0, "Mensagme de boas vindas."),
    DIRECIONAR_SETOR(1, "Mensagem de direcionamento de setor."),
    DIRECIONAR_FINANACEIRO(2, "Mensagem de direcionamento para o financeiro."),
    COLETA_NOME(3, "Mensagem de coleta de nome."),
    COLETA_SEGMENTO(4, "Mensagem de coleta de segmento."),
    COLETA_REGIAO(5, "Mensagem de coleta de região."),
    DIRECIONAR_PRIMEIRO_CONTATO(6, "Mensagem de direcionamento do primeiro contato."),
    DIRECIONAR_OUTRO_CONTATO_COMERCIAL(7, "Mensagem de direcionamento de outro contato do comercial."),
    ATENDIMENTO_ENCERRADO(8, "Mensagem de atendimento encerrado."),
    DIRECIONAR_OUTRO_CONTATO_FINANCEIRO(9, "Mensagem de direcionamento de outro contato do financeiro.");

    private final int codigo;
    private final String descricao;
}
