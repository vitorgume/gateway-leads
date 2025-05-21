package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensagemUseCase {

    private final MensagemGateway gateway;
    private final MensagemBuilder mensagemBuilder;

    public void enviarMensagem(String textoMensagem, String telefone) {
        log.info("Enviando mensagem. Texto: {}, Telefone: {}", textoMensagem, telefone);

        Mensagem mensagem = Mensagem.builder()
                .mensagem(textoMensagem)
                .telefone(telefone)
                .build();

        gateway.enviar(mensagem);

        log.info("Mensagem enviada com sucesso. Mensagem: {}", mensagem);
    }

    public void enviarContatoVendedor(Vendedor vendedor, Cliente cliente, String mensagem) {
        log.info("Enviando contato para vendedor. Vendedor: {}, Cliente: {}, Texto: {}", vendedor, cliente, mensagem);

        String textoMensagem = mensagemBuilder.getMensagem(TipoMensagem.DADOS_CONTATO_VENDEDOR, null, cliente);

        gateway.enviarContato(vendedor, cliente, mensagem);

        this.enviarMensagem(textoMensagem, vendedor.getTelefone());
        log.info("Contato enviado com sucesso para vendedor.");
    }

    public void enviarContatoFinanceiro(Cliente cliente) {
        log.info("Enviando contato para o financeiro. Cliente: {}", cliente);
        gateway.enviarContatoFinanceiro(cliente);
        log.info("Contato enviado com sucesso para financeiro.");
    }
}
