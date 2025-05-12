package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MensagemUseCase {

    private final MensagemGateway gateway;

    public void enviarMensagem(String mensagem) {
        gateway.enviar(mensagem);
    }

    public void enviarContatoVendedor(Vendedor vendedor, Cliente cliente, String mensagem) {
        gateway.enviarContato(vendedor, cliente, mensagem);
    }

    public void enviarContatoFinanceiro(Cliente cliente) {
        gateway.enviarContatoFinanceiro(cliente);
    }
}
