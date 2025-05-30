package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.SetorEnvioContato;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.ContatoRequestDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.DocumentoRequestDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.MensagemRequestDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ContatoMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.MensagemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

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

    @Value("${neoprint.financeiro.telefone}")
    private final String financeiroTelefone;

    @Value("${neoprint.logistica.telefone}")
    private final String logisticaTelefone;


    private final String MENSAGEM_ERRO_ENVIAR_MENSAGEM = "Erro ao enviar mensagem.";
    private final String MENSAGEM_ERRO_ENVIAR_CONTATO = "Erro ao enviar contato.";
    private final String MENSAGEM_ERRO_ENVIAR_CONTATO_FINANCEIRO = "Erro ao enviar contato financeiro.";
    private final String MENSAGEM_ERRO_ENVIAR_RELATORIO = "Erro ao enviar relatóro.";

    public MensagemDataProvider(
            WebClient webClient,
            @Value("${neoprint.ura.whatsapp.token}") String token,
            @Value("${neoprint.ura.whatsapp.id-instance}") String idInstance,
            @Value("${neoprint.ura.whatsapp.client-token}") String clienteToken,
            @Value("${neoprint.financeiro.telefone}") String financeiroTelefone,
            @Value("${neoprint.logistica.telefone}") String logisticaTelefone,
            @Value("${neoprint.gerencia.telefone}") String gerenciaTelefone
    ) {
        this.webClient = webClient;
        this.token = token;
        this.idInstance = idInstance;
        this.clienteToken = clienteToken;
        this.financeiroTelefone = financeiroTelefone;
        this.logisticaTelefone = logisticaTelefone;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        MensagemRequestDto body = MensagemMapper.paraRequestDto(mensagem);

        log.info(body.toString());
        String response = webClient
                .post()
                .uri("/instances/{idIsntance}/token/{token}/send-text", idInstance, token)
                .header("Client-Token", clienteToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(throwable -> {
                                    log.warn("Tentando novamente após erro ao enviar mensagem: {}", throwable.getMessage());
                                    return true;
                                })
                )
                .doOnError(e -> {
                    log.error("Erro ao enviar mensagem após tentativas.", e);
                    throw new DataProviderException(MENSAGEM_ERRO_ENVIAR_MENSAGEM, e.getCause());
                })
                .block();

        log.info("Response envio de mensagem: {}", response);
    }


    @Override
    public void enviarContato(Vendedor vendedor, Cliente cliente, String mensagem) {
        ContatoRequestDto body = ContatoMapper.paraRequestDto(cliente, vendedor.getTelefone());

        log.info(body.toString());

        String response = webClient
                .post()
                .uri("/instances/{idInstance}/token/{token}/send-contact", idInstance, token)
                .header("Client-Token", clienteToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> {
                    log.error("Erro ao enviar mensagem.", e);
                    throw new DataProviderException(MENSAGEM_ERRO_ENVIAR_CONTATO, e.getCause());
                })
                .block();

        log.info("Response envio de contato: {}", response);
    }

    @Override
    public void enviarContatoOutroSetor(Cliente cliente, SetorEnvioContato setor) {

        ContatoRequestDto body;

        if (setor.getCodigo() == 0) {
            body = ContatoMapper.paraRequestDto(cliente, financeiroTelefone);
        } else {
            body = ContatoMapper.paraRequestDto(cliente, logisticaTelefone);
        }

        log.info(body.toString());

        String response = webClient
                .post()
                .uri("/instances/{idInstance}/token/{token}/send-contact", idInstance, token)
                .header("Client-Token", clienteToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> {
                    log.error("Erro ao enviar mensagem.", e);
                    throw new DataProviderException(MENSAGEM_ERRO_ENVIAR_CONTATO_FINANCEIRO, e.getCause());
                })
                .block();

        log.info("Response envio de contato financeiro: {}", response);
    }

    @Override
    public void enviarRelatorio(String arquivo, String fileName, String telefone) {
        String base64ComPrefixo = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64," + arquivo;
        DocumentoRequestDto body = new DocumentoRequestDto(telefone, base64ComPrefixo, fileName);

        String response = webClient
                .post()
                .uri("/instances/{idInstance}/token/{token}/send-document/xlsx", idInstance, token)
                .header("Client-Token", clienteToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> {
                    log.error("Erro ao enviar relatório.", e);
                    throw new DataProviderException(MENSAGEM_ERRO_ENVIAR_RELATORIO, e.getCause());
                })
                .block();

        log.info("Response envio de relatório: {}", response);
    }
}
