package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemEscolhaInvalida implements MensagemType{

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Por favor, escolha uma opção válida.";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.ESCOLHA_INVALIDA.getCodigo();
    }
}
