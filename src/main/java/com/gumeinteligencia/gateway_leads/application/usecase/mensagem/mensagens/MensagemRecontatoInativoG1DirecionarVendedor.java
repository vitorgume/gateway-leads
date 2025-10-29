package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemRecontatoInativoG1DirecionarVendedor implements MensagemType {

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Perfeito, estou te redirecionando para o vendedor(a) " + nomeVendedor + " que logo entrará em contato. Muito obrigado ! Até...";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.RECONTATO_INATIVO_G1_DIRECIONAR_VENDEDOR.getCodigo();
    }
}
