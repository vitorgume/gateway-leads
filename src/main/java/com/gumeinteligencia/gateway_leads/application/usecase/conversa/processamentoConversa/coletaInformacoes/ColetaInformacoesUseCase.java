package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.VendedorUseCase;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColetaInformacoesUseCase {

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;
    private final VendedorUseCase vendedorUseCase;
    private final ProcessamentoColetaInformacoesUseCase processamentoColetaInformacoesUseCase;

    public void processarEtapaDeColeta(Mensagem mensagem, Cliente cliente, Conversa conversa) {
        MensagemColeta mensagemColeta = conversa.getMensagemColeta();

        ColetaType coletaType;

        if (!mensagemColeta.isColetaSegmento()) {
            coletaType = new ColetaSegmento(mensagemUseCase, conversaUseCase);
        } else if (!mensagemColeta.isColetaMunicipio()) {
            coletaType = new ColetaRegiao(mensagemUseCase, conversaUseCase, clienteUseCase);
        } else {
            coletaType = new FinalizaColeta(vendedorUseCase, mensagemUseCase, conversaUseCase, clienteUseCase);
        }

        ColetaType coletaTypeSetado = processamentoColetaInformacoesUseCase.setColetaType(coletaType);
        coletaTypeSetado.coleta(conversa, cliente, mensagem);
    }
}
