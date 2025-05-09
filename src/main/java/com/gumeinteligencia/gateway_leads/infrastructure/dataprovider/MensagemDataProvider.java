package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class MensagemDataProvider implements MensagemGateway {

//    private final WebClient webClient;
//
//    @Value("${neoprint.ura.whatsapp.key}")
//    private final String whatsAppKey;
//
//    public MensagemDataProvider(
//            WebClient webClient,
//            @Value("${neoprint.ura.whatsapp.key}") String whatsAppKey
//    ){
//        this.webClient = webClient;
//        this.whatsAppKey = whatsAppKey;
//    }

    @Override
    public void enviar(String mensagem) {
        //  Requisição para o envio de mensagem

        log.info(mensagem);
    }

    @Override
    public void enviarContato(Vendedor vendedor, Cliente cliente, String mensagem) {
        // Requisição para enviar contato
        log.info("{}. Nome: {}, Telefone: {}", mensagem, cliente.getNome(), cliente.getTelefone());
    }
}
