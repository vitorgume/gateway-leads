package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

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
public class DirecionamentoFinanceiro implements ProcessoFinalizadoType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if (conversa.getMensagemDirecionamento().isEscolhaFinanceiro()) {
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_OUTRO_CONTATO_FINANCEIRO, null));
            mensagemUseCase.enviarContatoFinanceiro(cliente);
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversaUseCase.salvar(conversa);
        } else {
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_FINANACEIRO, null));
            mensagemUseCase.enviarContatoFinanceiro(cliente);
            conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversaUseCase.salvar(conversa);
        }
    }

    @Override
    public Integer getTipoMensagem() {
        return EscolhaMensagem.ESCOLHA_FINANCEIRO.getCodigo();
    }
}
