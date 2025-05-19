package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class MensagemColetarNome implements MensagemType{

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Antes de continuar seu atendimento, me informa seu nome, por favor ? ";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.COLETA_NOME.getCodigo();
    }
}
