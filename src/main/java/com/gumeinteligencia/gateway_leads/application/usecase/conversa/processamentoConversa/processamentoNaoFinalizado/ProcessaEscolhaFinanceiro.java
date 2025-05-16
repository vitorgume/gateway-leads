package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

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
public class ProcessaEscolhaFinanceiro implements ProcessoNaoFinalizadoType {

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_FINANACEIRO, null));
        mensagemUseCase.enviarContatoFinanceiro(cliente);
        conversa.setFinalizada(true);
        conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
        conversaUseCase.salvar(conversa);
    }

    @Override
    public Integer getTipoMensage() {
        return EscolhaMensagem.ESCOLHA_FINANCEIRO.getCodigo();
    }
}
