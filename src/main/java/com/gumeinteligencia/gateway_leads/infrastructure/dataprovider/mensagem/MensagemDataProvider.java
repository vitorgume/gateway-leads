package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider.mensagem;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.ContatoRequestDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.DocumentoRequestDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.MensagemRequestDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ContatoMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.MensagemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Component
@Slf4j
@Profile("prod")
public class MensagemDataProvider implements MensagemGateway {

    private final WebClientExecutor executor;

    @Value("${neoprint.ura.whatsapp.token}")
    private final String token;

    @Value("${neoprint.ura.whatsapp.id-instance}")
    private final String idInstance;

    @Value("${neoprint.ura.whatsapp.client-token}")
    private final String clienteToken;

    public MensagemDataProvider(
            WebClientExecutor executor,
            @Value("${neoprint.ura.whatsapp.token}") String token,
            @Value("${neoprint.ura.whatsapp.id-instance}") String idInstance,
            @Value("${neoprint.ura.whatsapp.client-token}") String clienteToken
    ) {
        this.executor = executor;
        this.token = token;
        this.idInstance = idInstance;
        this.clienteToken = clienteToken;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        MensagemRequestDto body = MensagemMapper.paraRequestDto(mensagem);

        log.info(body.toString());

        Map<String, String> headers = Map.of("Client-Token", clienteToken);

        String uri = String.format("/instances/%s/token/%s/send-text", idInstance, token);

        executor.post(uri, body, headers, "Erro ao enviar mensagem.");
    }

    @Override
    public void enviarContato(String telefoneDestino, Cliente cliente) {
        ContatoRequestDto body = ContatoMapper.paraRequestDto(cliente, telefoneDestino);

        log.info(body.toString());

        Map<String, String> headers = Map.of("Client-Token", clienteToken);

        String uri = String.format("/instances/%s/token/%s/send-contact", idInstance, token);

        executor.post(uri, body, headers, "Erro ao enviar contato.");
    }

    @Override
    public void enviarRelatorio(String arquivo, String fileName, String telefone) {
        String base64ComPrefixo = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64," + arquivo;
        DocumentoRequestDto body = new DocumentoRequestDto(telefone, base64ComPrefixo, fileName);

        Map<String, String> headers = Map.of("Client-Token", clienteToken);

        String uri = String.format("/instances/%s/token/%s/send-document/xlsx", idInstance, token);

        executor.post(uri, body, headers, "Erro ao enviar relatório.");
    }
}
