package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

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
public class ProcessaEscolhaFinanceiro implements ProcessoNaoFinalizadoType {

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        mensagemUseCase.enviarMensagem(BuilderMensagens.direcinamnetoFinanceiro());
        mensagemUseCase.enviarContatoFinanceiro(cliente);
        conversa.setFinalizada(true);
        conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
        conversaUseCase.salvar(conversa);
    }
}
