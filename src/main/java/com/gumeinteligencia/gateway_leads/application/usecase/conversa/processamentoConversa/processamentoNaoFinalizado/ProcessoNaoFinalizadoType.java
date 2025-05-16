package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.EscolhaMensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public interface ProcessoNaoFinalizadoType {
    void processar(Conversa conversa, Cliente cliente, Mensagem mensagem);
    Integer getTipoMensage();
}
