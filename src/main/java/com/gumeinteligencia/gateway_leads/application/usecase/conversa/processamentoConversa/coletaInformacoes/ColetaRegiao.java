package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.ATENDIMENTO_ENCERRADO, null, null), cliente.getTelefone(), conversa);
        } else {
            conversa.setTipoUltimaMensagem(TipoMensagem.COLETA_SEGMENTO);
            cliente.setSegmento(GatewayEnum.gatewaySegmento(mensagem.getMensagem()));
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.COLETA_REGIAO, null, null), cliente.getTelefone(), conversa);
        }

        log.info("Coleta de região concluida com sucesso. Conversa: {}, Cliente: {}", conversa, cliente);
    }

    @Override
    public boolean deveAplicar(List<EstadoColeta> estados) {
        return estados.contains(EstadoColeta.COLETA_REGIAO);
    }

    @Override
    public TipoMensagem getTipoMensagem() {
        return TipoMensagem.COLETA_REGIAO;
    }
}
