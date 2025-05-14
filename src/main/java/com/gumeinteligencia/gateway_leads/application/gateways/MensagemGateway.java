package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;

public interface MensagemGateway {
    void enviar(Mensagem mensagem);
    void enviarContato(Vendedor vendedor, Cliente cliente, String mensagem);
    void enviarContatoFinanceiro(Cliente cliente);
}
