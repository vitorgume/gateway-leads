package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

public class MensagemDirecionarPrimeiroContato implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor) {
        return "Muito obrigado pelas informações ! Agora você será redirecionado para o(a) "
                + nomeVendedor + ", em alguns minutos ele(a) entrará em contato com você ! Até...";
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_PRIMEIRO_CONTATO.getCodigo();
    }
}
