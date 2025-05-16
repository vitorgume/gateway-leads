package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.EscolhaMensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessaColeta implements ProcessoNaoFinalizadoType{

    private final ColetaInformacoesUseCase coletaInformacoesUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        coletaInformacoesUseCase.processarEtapaDeColeta(mensagem, cliente, conversa);
    }

    @Override
    public Integer getTipoMensage() {
        return 4;
    }
}
