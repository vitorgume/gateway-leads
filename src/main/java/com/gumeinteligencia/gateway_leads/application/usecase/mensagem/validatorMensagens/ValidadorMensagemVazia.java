package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.validatorMensagens;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import org.springframework.stereotype.Service;

@Service
public class ValidadorMensagemVazia implements MensagemValidator {
    @Override
    public boolean deveIgnorar(Mensagem mensagem) {
        return mensagem.getMensagem() == null || mensagem.getMensagem().isBlank();
    }
}
