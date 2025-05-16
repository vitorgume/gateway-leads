package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.EscolhaMensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessaEncerramento implements ProcessoNaoFinalizadoType {

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.ATENDIMENTO_ENCERRADO, null));
        conversaUseCase.deletar(conversa.getId());
        clienteUseCase.deletar(cliente.getId());
    }

    @Override
    public Integer getTipoMensage() {
        return EscolhaMensagem.ESCOLHA_ENCERRAMENTO.getCodigo();
    }
}
