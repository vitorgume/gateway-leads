package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessarMensagemUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ProcessamentoConversaUseCase processamentoConversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;

    public String processarNovaMensagem(Mensagem mensagem) {
        log.info("Processando nova mensagem. Mensagem: {}", mensagem);

        clienteUseCase
                .consultarPorTelefone(mensagem.getTelefone())
                .ifPresentOrElse(
                        cliente -> processarConversaExistente(cliente, mensagem),
                        () -> iniciarNovaConversa(mensagem)
                );

        log.info("Mensagem processada com sucesso.");

        return "";
    }

    private void processarConversaExistente(Cliente cliente, Mensagem mensagem) {
        log.info("Processando mensagem de uma conversa já existente. Cliente: {}, Mensagem: {}", cliente, mensagem);

        Conversa conversa = conversaUseCase.consultarPorCliente(cliente);


        if(!conversa.getFinalizada()) {
            processamentoConversaUseCase.processarConversaNaoFinalizada(conversa, cliente, mensagem);
        } else {
            processamentoConversaUseCase.processarConversaFinalizada(conversa, cliente, mensagem);
        }

        log.info("Processamento de mensagem de uma conversa já existente conclúido com sucesso.");
    }

    private void iniciarNovaConversa(Mensagem mensagem) {
        log.info("Processando início de uma conversa. Mensagem: {}", mensagem);

        Cliente novoCliente = Cliente.builder().telefone(mensagem.getTelefone()).build();
        Cliente cliente = clienteUseCase.cadastrar(novoCliente);
        Conversa novaConversa = conversaUseCase.criar(cliente);
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.BOAS_VINDAS, null, null), cliente.getTelefone(), null);
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.COLETA_NOME, null, null), cliente.getTelefone(), novaConversa);

        log.info("Conversa iniciada com sucesso.");
    }






}
