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

    public String gateway(Mensagem mensagem) {
        Optional<Cliente> clienteOptional = clienteuseCase.consultarPorTelefone(mensagem.getTelefone());

        if(clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Conversa conversa = conversaUseCase.consultarPorCliente(cliente);

            if(conversa.getFinalizada()) {
                chatUseCase.direcionarVendedor(cliente, conversa);
            }
            chatUseCase.coletarInformacoes(mensagem, cliente, conversa);
        } else {
            Cliente novoCliente = Cliente.builder().telefone(mensagem.getTelefone()).build();
            Cliente cliente = clienteuseCase.cadastrar(novoCliente);
            Conversa conversa = conversaUseCase.criar(cliente);
            chatUseCase.coletarInformacoes(mensagem, cliente, conversa);
        }

        return "";
    }
}
