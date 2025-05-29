package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.OutroContatoNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.domain.OutroContato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutroContatoUseCase {

    private final OutroContatoGateway outroContatoGateway;


    public OutroContato consultarPorNome(String nome) {
        Optional<OutroContato> outroContato = outroContatoGateway.consultarPorNome(nome);

        if(outroContato.isEmpty()) {
            throw new OutroContatoNaoEncontradoException();
        }

        return outroContato.get();
    }
}
