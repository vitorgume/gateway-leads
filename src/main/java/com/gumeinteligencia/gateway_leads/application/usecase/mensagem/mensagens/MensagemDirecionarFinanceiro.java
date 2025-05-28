package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarFinanceiro implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Muito bem ! Agora você será redirecionado para a Vitória, responsável pelo nosso financeiro. Logo elá entrará em contato com você. Até ...";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_FINANACEIRO.getCodigo();
    }
}
