package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemSeparacaoContatosVendedores implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "✳️✳️✳️✳️✳️✳️";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.SEPARACAO_CONTATOS.getCodigo();
    }
}
