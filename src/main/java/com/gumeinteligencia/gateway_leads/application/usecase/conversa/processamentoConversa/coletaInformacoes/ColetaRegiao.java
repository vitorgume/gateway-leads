package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColetaRegiao implements ColetaType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        cliente.setSegmento(GatewayEnum.gatewaySegmento(mensagem.getMensagem()));
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.COLETA_REGIAO, null));
        conversa.getMensagemColeta().setColetaMunicipio(true);
        conversa.setUltimaMensagem(LocalDateTime.now());
        conversaUseCase.salvar(conversa);
        clienteUseCase.salvar(cliente);
    }

    @Override
    public boolean deveAplicar(MensagemColeta estado) {
        return !estado.isColetaMunicipio();
    }
}
