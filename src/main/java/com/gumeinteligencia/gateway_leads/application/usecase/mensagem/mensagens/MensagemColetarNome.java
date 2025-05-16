package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import org.springframework.stereotype.Component;

@Component
public class MensagemColetarNome implements MensagemType{

    @Override
    public String getMensagem(String nomeVendedor) {
        return "Antes de continuar seu atendimento, me informa seu nome, por favor ? ";
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.COLETA_NOME.getCodigo();
    }
}
