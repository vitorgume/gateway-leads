package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class ColetaRegiao implements ColetaType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Coletando região. Conversa:{}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);

        if(mensagem.getMensagem().equals("0")) {
            conversaUseCase.encerrar(conversa.getId());
            clienteUseCase.inativar(cliente.getId());
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.ATENDIMENTO_ENCERRADO, null, null), cliente.getTelefone());
        } else {
            cliente.setSegmento(GatewayEnum.gatewaySegmento(mensagem.getMensagem()));
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.COLETA_REGIAO, null, null), cliente.getTelefone());
            conversa.getMensagemColeta().setColetaRegiao(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);
        }

        log.info("Coleta de região concluida com sucesso. Conversa: {}, Cliente: {}", conversa, cliente);
    }

    @Override
    public boolean deveAplicar(MensagemColeta estado) {
        return !estado.isColetaRegiao();
    }

    @Override
    public TipoMensagem getTipoMensagem() {
        return TipoMensagem.COLETA_REGIAO;
    }
}
