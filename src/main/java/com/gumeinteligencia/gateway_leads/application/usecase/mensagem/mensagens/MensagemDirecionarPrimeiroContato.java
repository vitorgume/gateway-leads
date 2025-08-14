package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarPrimeiroContato implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Muito obrigado pelas informações ! Agora você será redirecionado para o(a) "
                + nomeVendedor + ", logo entrará em contato com você ! Até..." + "\n Dia 15/08/2025 não estaremos atendendo devido ao feriado municipal";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_PRIMEIRO_CONTATO.getCodigo();
    }
}
