package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.OutroContatoUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import com.gumeinteligencia.gateway_leads.domain.outroContato.Setor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.EscolhaMensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessaEscolhaFinanceiro implements ProcessoNaoFinalizadoType {

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemBuilder mensagemBuilder;
    private final OutroContatoUseCase outroContatoUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando escolha do financeiro de uma conversa não finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);

        OutroContato outroContato = outroContatoUseCase.consultarPorNome("Vitoria");

        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_FINANACEIRO, null, null), cliente.getTelefone(), conversa);
        mensagemUseCase.enviarContatoOutroSetor(cliente, outroContato);
        conversa.setFinalizada(true);
        conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
        conversaUseCase.salvar(conversa);
        log.info("Processamento de escolha do financeiro de uma conversa não finalizada concluida com sucesso. Conversa: {}", conversa);
    }

    @Override
    public Integer getTipoMensage() {
        return EscolhaMensagem.ESCOLHA_FINANCEIRO.getCodigo();
    }
}
