package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class MensagemEncerramento implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Atendimento encerrado. Até logo...";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.ATENDIMENTO_ENCERRADO.getCodigo();
    }
}
