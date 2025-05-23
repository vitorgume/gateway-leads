package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarOutroContatoLogistica implements MensagemType{

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Identifiquei que você já estava em conversa com a Gabriella, vou repassar você novamente para ela.";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_OUTRO_CONTATO_LOGISTICA.getCodigo();
    }
}
