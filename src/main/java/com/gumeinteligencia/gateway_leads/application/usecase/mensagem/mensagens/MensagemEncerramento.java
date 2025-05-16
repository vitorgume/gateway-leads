package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import org.springframework.stereotype.Component;

@Component
public class MensagemEncerramento implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor) {
        return "Atendimento encerrado. Até logo...";
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.ATENDIMENTO_ENCERRADO.getCodigo();
    }
}
