package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.SetorEnvioContato;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.EscolhaMensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DirecionamentoLogistica implements ProcessoFinalizadoType {

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando escolha da logística de uma conversa finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);

        if(conversa.getMensagemDirecionamento().isEscolhaLogistica()) {
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_OUTRO_CONTATO_LOGISTICA, null, null), cliente.getTelefone(), conversa);
            mensagemUseCase.enviarContatoOutroSetor(cliente, SetorEnvioContato.LOGISTICA);
            conversa.getMensagemDirecionamento().setMensagemInicial(true);
            conversaUseCase.salvar(conversa);
        } else {
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_LOGISTICA, null, null), cliente.getTelefone(), conversa);
            mensagemUseCase.enviarContatoOutroSetor(cliente, SetorEnvioContato.LOGISTICA);
            conversa.getMensagemDirecionamento().setEscolhaLogistica(true);
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversaUseCase.salvar(conversa);
        }

        log.info("Processamento de escolha da logística concluida com sucesso. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
    }

    @Override
    public Integer getTipoMensagem() {
        return EscolhaMensagem.ESCOLHA_LOGISTICA.getCodigo();
    }
}
