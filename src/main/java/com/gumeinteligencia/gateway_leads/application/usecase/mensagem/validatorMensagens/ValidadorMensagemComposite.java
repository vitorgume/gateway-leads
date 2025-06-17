package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.validatorMensagens;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidadorMensagemComposite {

    private final List<MensagemValidator> validadores;

    public boolean deveIgnorar(Mensagem mensagem) {
        return validadores.stream().anyMatch(validator -> validator.deveIgnorar(mensagem));
    }
}
