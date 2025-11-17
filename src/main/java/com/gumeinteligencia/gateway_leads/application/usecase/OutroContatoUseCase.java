package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.OutroContatoNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.OutroContatoGateway;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutroContatoUseCase {

    private final OutroContatoGateway gateway;


    public OutroContato consultarPorNome(String nome) {
        Optional<OutroContato> outroContato = gateway.consultarPorNome(nome);

        if(outroContato.isEmpty()) {
            throw new OutroContatoNaoEncontradoException();
        }

        return outroContato.get();
    }

    public List<OutroContato> listar() {
        return gateway.listar();
    }

    @Transactional(readOnly = true)
    public boolean existeTelefone(String telefone) {
        return gateway.existeTelefone(telefone);
    }

}
