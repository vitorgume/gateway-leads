package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ColetaRegiao implements ColetaType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;

    @Override
    public void coleta(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        cliente.setSegmento(GatewayEnum.gatewaySegmento(mensagem.getMensagem()));
        mensagemUseCase.enviarMensagem(BuilderMensagens.coletaRegiao());
        conversa.getMensagemColeta().setColetaMunicipio(true);
        conversa.setUltimaMensagem(LocalDateTime.now());
        conversaUseCase.salvar(conversa);
        clienteUseCase.salvar(cliente);
    }
}
