package com.gumeinteligencia.gateway_leads.application.gateways;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public interface MensagemGateway {
    void enviar(Mensagem mensagem);
    void enviarContato(String telefoneDestino, Cliente cliente);
    void enviarRelatorio(String arquivo, String fileName, String telefone);
}
