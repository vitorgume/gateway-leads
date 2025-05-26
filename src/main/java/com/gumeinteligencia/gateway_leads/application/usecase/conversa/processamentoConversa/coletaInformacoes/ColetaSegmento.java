package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
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
@Order(1)
public class ColetaSegmento implements ColetaType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Coletando segmento. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.COLETA_SEGMENTO, null, null), cliente.getTelefone(), conversa);
        conversa.getMensagemColeta().setColetaSegmento(true);
        conversa.setDataUltimaMensagem(LocalDateTime.now());
        conversaUseCase.salvar(conversa);
        log.info("Coleta de segmento concluida com sucesso. Conversa: {}, Cliente: {}", conversa, cliente);
    }

    @Override
    public boolean deveAplicar(MensagemColeta estado) {
        return !estado.isColetaSegmento();
    }

    @Override
    public TipoMensagem getTipoMensagem() {
        return TipoMensagem.COLETA_SEGMENTO;
    }
}
