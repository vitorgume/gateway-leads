package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessaEscolhaComercial implements ProcessoNaoFinalizadoType {

    private final ConversaUseCase conversaUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        conversa.getMensagemDirecionamento().setEscolhaComercial(true);
        conversa = conversaUseCase.salvar(conversa);
        this.coletaInformacoesUseCase.processarEtapaDeColeta(mensagem, cliente, conversa);
    }
}
