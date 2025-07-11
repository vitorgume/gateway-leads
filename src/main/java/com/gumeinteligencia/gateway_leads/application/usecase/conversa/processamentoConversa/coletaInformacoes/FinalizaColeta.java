package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class FinalizaColeta implements ColetaType{

    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;
    private final MensagemBuilder mensagemBuilder;
    private final RelatorioUseCase relatorioUseCase;

    @Override
    public void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Finalizando coleta de informações. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);

        if(mensagem.getMensagem().equals("0")) {
            conversaUseCase.encerrar(conversa.getId());
            clienteUseCase.inativar(cliente.getId());
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.ATENDIMENTO_ENCERRADO, null, null), cliente.getTelefone(), conversa);
        } else {
            conversa.setTipoUltimaMensagem(TipoMensagem.COLETA_REGIAO);
            conversaUseCase.salvar(conversa);
            cliente.setRegiao(GatewayEnum.gatewayRegiao(mensagem.getMensagem()));
            Vendedor vendedor = vendedorUseCase.escolherVendedor(cliente);
            conversa.setVendedor(vendedor);
            conversa.setFinalizada(true);
            conversa.setUltimaMensagemConversaFinalizada(LocalDateTime.now());
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_PRIMEIRO_CONTATO, vendedor.getNome(), null), cliente.getTelefone(), conversa);
            mensagemUseCase.enviarContatoVendedor(vendedor, cliente);
            relatorioUseCase.atualizarRelatorioOnline(cliente, vendedor);
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);

            if(conversa.getMensagemDirecionamento().isEscolhaComercialRecontato()) {
                conversa.getMensagemDirecionamento().setMensagemInicial(false);
                conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                conversaUseCase.salvar(conversa);
            }
        }

        log.info("Finalização de coleta de informações concluida com sucesso. Conversa: {}, Cliente: {}", conversa, cliente);
    }

    @Override
    public boolean deveAplicar(MensagemColeta estado) {
        return estado.isColetaSegmento() && estado.isColetaRegiao();
    }

    @Override
    public TipoMensagem getTipoMensagem() {
        return null;
    }
}
