package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinalizaColeta implements ColetaType{

    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;

    @Override
    public void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        cliente.setRegiao(GatewayEnum.gatewayRegiao(mensagem.getMensagem()));
        Vendedor vendedor = vendedorUseCase.escolherVendedor(cliente);
        conversa.setVendedor(vendedor);
        conversa.setFinalizada(true);
        mensagemUseCase.enviarMensagem(
                BuilderMensagens.direcionamentoPrimeiroContato(cliente.getNome(), vendedor.getNome()));
        mensagemUseCase.enviarContatoVendedor(vendedor, cliente, "Contato novo");
        conversaUseCase.salvar(conversa);
        clienteUseCase.salvar(cliente);

        if(conversa.getMensagemDirecionamento().isEscolhaComercialRecontato()) {
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversa.getMensagemDirecionamento().setEscolhaComercial(true);
            conversaUseCase.salvar(conversa);
        }
    }
}
