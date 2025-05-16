package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessaColeta implements ProcessoNaoFinalizadoType{

    private final ColetaInformacoesUseCase coletaInformacoesUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando coleta de informações. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
        coletaInformacoesUseCase.processarEtapaDeColeta(mensagem, cliente, conversa);
        log.info("Processamento de coleta de informações concluida com sucesso.");
    }

    @Override
    public Integer getTipoMensage() {
        return 4;
    }
}
