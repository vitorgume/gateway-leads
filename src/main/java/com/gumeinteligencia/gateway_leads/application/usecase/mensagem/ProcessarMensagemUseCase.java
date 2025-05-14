package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.ProcessamentoConversaUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessarMensagemUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ProcessamentoConversaUseCase processamentoConversaUseCase;
    private final MensagemUseCase mensagemUseCase;

    public String gateway(Mensagem mensagem) {
        clienteUseCase
                .consultarPorTelefone(mensagem.getTelefone())
                .ifPresentOrElse(
                        cliente -> processarConversaExistente(cliente, mensagem),
                        () -> iniciarNovaConversa(mensagem)
                );

        return "";
    }

    private void processarConversaExistente(Cliente cliente, Mensagem mensagem) {
        Conversa conversa = conversaUseCase.consultarPorCliente(cliente);


        if(!conversa.getFinalizada()) {
            processamentoConversaUseCase.processarConversaFinalizada(conversa, cliente, mensagem);
        } else {
            processamentoConversaUseCase.processarConversaNaoFinalizada(conversa, cliente, mensagem);
        }
    }

    private void iniciarNovaConversa(Mensagem mensagem) {
        Cliente novoCliente = Cliente.builder().telefone(mensagem.getTelefone()).build();
        Cliente cliente = clienteUseCase.cadastrar(novoCliente);
        conversaUseCase.criar(cliente);
        mensagemUseCase.enviarMensagem(BuilderMensagens.boasVindas());
        mensagemUseCase.enviarMensagem(BuilderMensagens.coletaNome());
    }






}
