package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GatewayMensagemUseCase {

    private final ClienteUseCase clienteuseCase;
    private final ConversaUseCase conversaUseCase;
    private final ChatUseCase chatUseCase;
    private final MensagemUseCase mensagemUseCase;

    public String gateway(Mensagem mensagem) {
        Optional<Cliente> clienteOptional = clienteuseCase.consultarPorTelefone(mensagem.getTelefone());


        if(clienteOptional.isPresent()) {
            fluxoConversaExistente(clienteOptional.get(), mensagem);
        } else {
            fluxoConversaNaoExistente(mensagem);
        }

        return "";
    }

    private void fluxoConversaExistente(Cliente cliente, Mensagem mensagem) {
        Conversa conversa = conversaUseCase.consultarPorCliente(cliente);


        if(!conversa.getFinalizada()) {
            chatUseCase.conversaFinalizada(conversa, cliente, mensagem);
        } else {
            chatUseCase.conversaNaoFinalizada(conversa, cliente, mensagem);
        }
    }

    private void fluxoConversaNaoExistente(Mensagem mensagem) {
        Cliente novoCliente = Cliente.builder().telefone(mensagem.getTelefone()).build();
        Cliente cliente = clienteuseCase.cadastrar(novoCliente);
        conversaUseCase.criar(cliente);
        mensagemUseCase.enviarMensagem(BuilderMensagens.boasVindas());
        mensagemUseCase.enviarMensagem(BuilderMensagens.coletaNome());
    }






}
