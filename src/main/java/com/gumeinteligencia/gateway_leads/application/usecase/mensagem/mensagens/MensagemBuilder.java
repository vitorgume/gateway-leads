package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MensagemBuilder {

    private final MensagemFactory mensagemFactory;

    public String getMensagem(TipoMensagem tipoMensagem, String nomeVendedor, Cliente cliente) {
        return mensagemFactory.create(tipoMensagem).getMensagem(nomeVendedor, cliente);
    }
}
