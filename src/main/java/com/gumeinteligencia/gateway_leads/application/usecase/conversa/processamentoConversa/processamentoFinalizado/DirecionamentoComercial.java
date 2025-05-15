package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.BuilderMensagens;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirecionamentoComercial implements ProcessoFinalizadoType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()));
            mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente, "Recontato");
            conversa.getMensagemDirecionamento().setEscolhaComercial(true);
            conversa.getMensagemDirecionamento().setMensagemInicial(false);
            conversaUseCase.salvar(conversa);
        } else {
            coletaInformacoesUseCase.processarEtapaDeColeta(mensagem, cliente, conversa);
            conversa.getMensagemDirecionamento().setEscolhaComercialRecontato(true);
            conversaUseCase.salvar(conversa);
        }
    }
}
