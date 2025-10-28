package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.validatorMensagens;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public class ValidadorImagem implements MensagemValidator {
    @Override
    public boolean deveIgnorar(Mensagem mensagem) {
        return mensagem.getMensagem().equals("Midia do usuário");
    }
}
