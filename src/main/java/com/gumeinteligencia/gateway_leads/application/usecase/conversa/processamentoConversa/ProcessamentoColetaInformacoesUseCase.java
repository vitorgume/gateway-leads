package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Setter
public class ProcessamentoColetaInformacoesUseCase {

    private ColetaType coletaType;

    public void processarEtapaDeColeta(Mensagem mensagem, Cliente cliente, Conversa conversa) {
        MensagemColeta mensagemColeta = conversa.getMensagemColeta();

        this.coletaType.coleta(conversa, cliente, mensagem);

//        if (!mensagemColeta.isColetaSegmento()) {
//
//        } else if (!mensagemColeta.isColetaMunicipio()) {
//
//        } else {
//
//        }
    }
}
