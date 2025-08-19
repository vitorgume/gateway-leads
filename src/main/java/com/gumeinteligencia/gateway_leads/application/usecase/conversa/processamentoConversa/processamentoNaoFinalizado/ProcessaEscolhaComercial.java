package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import com.gumeinteligencia.gateway_leads.domain.mensagem.EscolhaMensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessaEscolhaComercial implements ProcessoNaoFinalizadoType {

    private final ConversaUseCase conversaUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando escolha comercial de uma conversa não finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
        conversa.getMensagemDirecionamento().add(MensagemDirecionamento.ESCOLHA_COMERCIAL);
        conversa.getMensagemColeta().add(EstadoColeta.COLETA_SEGMENTO);
        conversa = conversaUseCase.salvar(conversa);
        this.coletaInformacoesUseCase.processarEtapaDeColeta(mensagem, cliente, conversa);
        log.info("Processamento de escolha comercial de uma conversa não finalizada concluido com sucesso. Conversa: {}", conversa);
    }

    @Override
    public Integer getTipoMensage() {
        return EscolhaMensagem.ESCOLHA_COMERCIAL.getCodigo();
    }
}
