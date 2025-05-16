package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.ContatoRequestDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.MensagemRequestDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ContatoMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.MensagemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class MensagemDataProvider implements MensagemGateway {

    private final WebClient webClient;

    @Value("${neoprint.ura.whatsapp.token}")
    private final String token;

    @Value("${neoprint.ura.whatsapp.id-instance}")
    private final String idInstance;

    @Value("${neoprint.ura.whatsapp.client-token}")
    private final String clienteToken;

    public MensagemDataProvider(
            WebClient webClient,
            @Value("${neoprint.ura.whatsapp.token}") String token,
            @Value("${neoprint.ura.whatsapp.id-instance}") String idInstance,
            @Value("${neoprint.ura.whatsapp.client-token}") String clienteToken
    ){
        this.webClient = webClient;
        this.token = token;
        this.idInstance = idInstance;
        this.clienteToken = clienteToken;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        MensagemRequestDto body = MensagemMapper.paraRequestDto(mensagem);

        String response = webClient
                .post()
                .uri("/instances/{idIsntance}/token/{token}/send-text", idInstance, token)
                .header("Client-Token", clienteToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Erro ao enviar mensagem: " + e.getMessage()))
                .block();

        log.info("Response envio de mensagem: {}", response);
    }


    @Override
    public void enviarContato(Vendedor vendedor, Cliente cliente, String mensagem) {
        ContatoRequestDto body = ContatoMapper.paraRequestDto(cliente, vendedor.getTelefone());


        String response = webClient
                .post()
                .uri("/instances/{idInstance}/token/{token}/send-contact", idInstance, token)
                .header("Client-Token", clienteToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Erro ao enviar mensagem: " + e.getMessage()))
                .block();

        log.info("Response envio de mensagem: {}", response);
    }

    @Override
    public void enviarContatoFinanceiro(Cliente cliente) {
        ContatoRequestDto body = ContatoMapper.paraRequestDto(cliente, "");


        String response = webClient
                .post()
                .uri("/instances/{idInstance}/token/{token}/send-contact", idInstance, token)
                .header("Client-Token", clienteToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Erro ao enviar mensagem: " + e.getMessage()))
                .block();

        log.info("Response envio de mensagem: {}", response);
    }
}
