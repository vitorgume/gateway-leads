package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.BuilderMensagens;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirecionamentoFinanceiro implements ProcessoFinalizadoType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if (conversa.getMensagemDirecionamento().isEscolhaFinanceiro()) {
            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContatoFinanceiro());
            mensagemUseCase.enviarContatoFinanceiro(cliente);
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversaUseCase.salvar(conversa);
        } else {
            mensagemUseCase.enviarMensagem(BuilderMensagens.direcinamnetoFinanceiro());
            mensagemUseCase.enviarContatoFinanceiro(cliente);
            conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversaUseCase.salvar(conversa);
        }
    }
}
