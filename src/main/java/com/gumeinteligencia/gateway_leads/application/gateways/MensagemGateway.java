package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;

public interface MensagemGateway {
    void enviar(String mensagem);
    void enviarContato(Vendedor vendedor, Cliente cliente, String mensagem);
    void enviarContatoFinanceiro(Cliente cliente);
}
