package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalizaColeta implements ColetaType{

    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        cliente.setRegiao(GatewayEnum.gatewayRegiao(mensagem.getMensagem()));
        Vendedor vendedor = vendedorUseCase.escolherVendedor(cliente);
        conversa.setVendedor(vendedor);
        conversa.setFinalizada(true);
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_PRIMEIRO_CONTATO, null));
        mensagemUseCase.enviarContatoVendedor(vendedor, cliente, "Contato novo");
        conversaUseCase.salvar(conversa);
        clienteUseCase.salvar(cliente);

        if(conversa.getMensagemDirecionamento().isEscolhaComercialRecontato()) {
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversa.getMensagemDirecionamento().setEscolhaComercial(true);
            conversaUseCase.salvar(conversa);
        }
    }

    @Override
    public boolean deveAplicar(MensagemColeta estado) {
        return estado.isColetaSegmento() && estado.isColetaMunicipio();
    }
}
