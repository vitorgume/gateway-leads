package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

import java.util.List;

public interface ColetaType {
    void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem);
    boolean deveAplicar(List<EstadoColeta> estado);
    TipoMensagem getTipoMensagem();
}
