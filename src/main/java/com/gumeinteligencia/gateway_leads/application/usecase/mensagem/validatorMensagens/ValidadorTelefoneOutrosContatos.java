package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.validatorMensagens;

import com.gumeinteligencia.gateway_leads.application.usecase.OutroContatoUseCase;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidadorTelefoneOutrosContatos implements MensagemValidator {

    private final OutroContatoUseCase outroContatoUseCase;

    @Override
    public boolean deveIgnorar(Mensagem mensagem) {
        return !outroContatoUseCase.listar().stream().map(OutroContato::getTelefone).toList().contains(mensagem.getTelefone());
    }
}
