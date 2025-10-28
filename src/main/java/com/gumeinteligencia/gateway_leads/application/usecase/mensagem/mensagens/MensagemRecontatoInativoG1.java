package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemRecontatoInativoG1 implements MensagemType {
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Mensagem de recontato";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.RECONTATO_INATIVO_G1.getCodigo();
    }
}
