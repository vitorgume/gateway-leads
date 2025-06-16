package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.validatorMensagens;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public interface MensagemValidator {
    boolean deveIgnorar(Mensagem mensagem);
}
