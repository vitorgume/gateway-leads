package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoInativo;

import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirecionamentoRecontatoEncerramento implements ProcessamentoConversaInativa {

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void processar(Cliente cliente, Conversa conversa) {
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.ATENDIMENTO_ENCERRADO, null, null), cliente.getTelefone(), conversa);
        conversaUseCase.encerrar(conversa.getId());
        clienteUseCase.inativar(cliente.getId());
    }

    @Override
    public String mensagem() {
        return "0";
    }
}
