package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarFinanceiro implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor) {
        return "Muito bem ! Agora você será redirecionado para a Patrícia, responsável pelo nosso financeiro. Em alguns minutos elá entrará em contato com você. Até ...";
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_FINANACEIRO.getCodigo();
    }
}
