package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColetaInformacoesUseCase {

    private final ColetaFactory coletaFactory;

    public void processarEtapaDeColeta(Mensagem mensagem, Cliente cliente, Conversa conversa) {
        MensagemColeta mensagemColeta = conversa.getMensagemColeta();

        ColetaType coletaType = coletaFactory.create(mensagemColeta);

        coletaType.coleta(conversa, cliente, mensagem);
    }
}
