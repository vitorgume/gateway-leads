package com.gumeinteligencia.gateway_leads.domain.mensagem;

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
    DIRECIONAR_OUTRO_CONTATO_FINANCEIRO(9, "Mensagem de direcionamento de outro contato do financeiro."),
    DADOS_CONTATO_VENDEDOR(10, "Mensagem com os dados do contato enviado para o vendedor."),
    ESCOLHA_INVALIDA(11, "Mensagem para quando o usuário fizer uma escolha invalida das opções."),
    DIRECIONAR_LOGISTICA(12, "Mensagem de direcionamento para logística."),
    DIRECIONAR_OUTRO_CONTATO_LOGISTICA(13, "Mensagem de direcionamento de outro contato da logistica."),
    CONTATO_INATIVO(14, "Mensgem de contato inativo."),
    SEPARACAO_CONTATOS(15, "Mensagem de separação entre os contatos enviados para o vendedor.");

    private final Integer codigo;
    private final String descricao;
}
