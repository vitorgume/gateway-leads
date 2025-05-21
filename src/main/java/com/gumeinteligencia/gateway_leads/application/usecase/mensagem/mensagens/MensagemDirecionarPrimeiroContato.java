package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarPrimeiroContato implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Muito obrigado pelas informações ! Agora você será redirecionado para o(a) "
                + nomeVendedor + ", em alguns minutos ele(a) entrará em contato com você ! Até...";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_PRIMEIRO_CONTATO.getCodigo();
    }
}
