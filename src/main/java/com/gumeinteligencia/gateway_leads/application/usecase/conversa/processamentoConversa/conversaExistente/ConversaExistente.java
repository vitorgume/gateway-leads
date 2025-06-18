package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.conversaExistente;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public interface ConversaExistente {
    void processarConversaNaoFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem);
    void processarConversaFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem);
    void processarConversaExistente(Cliente cliente, Mensagem mensagem);

}
