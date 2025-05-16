package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public interface ColetaType {
    void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem);
    boolean deveAplicar(MensagemColeta estado);
}
