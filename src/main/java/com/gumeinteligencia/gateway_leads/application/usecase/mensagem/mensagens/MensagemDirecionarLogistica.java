package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarLogistica implements MensagemType {

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return "Muito bem ! Agora você será direcionado para a Gabriella, responsável pela nossa logística. Logo elá entrará em contato com você. Até ..." + "\nDia 15/08/2025 não estaremos atendendo devido ao feriado municipal";
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_LOGISTICA.getCodigo();
    }
}
