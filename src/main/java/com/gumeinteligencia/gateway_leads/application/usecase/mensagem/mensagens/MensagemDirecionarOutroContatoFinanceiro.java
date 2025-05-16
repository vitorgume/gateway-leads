package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

public class MensagemDirecionarOutroContatoFinanceiro implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor) {
        return "Identifiquei que você já estava em conversa com a Patrícia, vou repassar você novamente para ela.";
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_OUTRO_CONTATO_FINANCEIRO.getCodigo();
    }
}
