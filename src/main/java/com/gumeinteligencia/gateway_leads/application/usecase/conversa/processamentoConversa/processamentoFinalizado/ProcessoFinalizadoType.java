package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public interface ProcessoFinalizadoType {
    void processar(Conversa conversa, Cliente cliente, Mensagem mensagem);
    Integer getTipoMensagem();
}
