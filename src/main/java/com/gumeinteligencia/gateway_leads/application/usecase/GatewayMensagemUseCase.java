package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Conversa;
import com.gumeinteligencia.gateway_leads.domain.Mensagem;
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
        Optional<Cliente> clienteOptional = clienteuseCase.consultarPorTelefone(mensagem.getMensagem());

        if(clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Conversa conversa = conversaUseCase.consultarPorCliente(cliente);

            if(conversa.getFinalizada()) {
                chatUseCase.direcionarVendedor(cliente);
            }
            chatUseCase.coletarInformacoes(mensagem);
        } else {
            Cliente novoCliente = Cliente.builder().telefone(mensagem.getTelefone()).build();
            Cliente cliente = clienteuseCase.cadastrar(novoCliente);
            conversaUseCase.criar(cliente);
            chatUseCase.coletarInfromacoes(mensagem);
        }
    }
}
