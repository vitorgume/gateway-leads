package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoInativo;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial.MensagemOrquestradora;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProcessamentoConversaInativaUseCase {

    private final MensagemBuilder mensagemBuilder;
    private final MensagemOrquestradora mensagemOrquestradora;
    private final ProcessamentoConversaInativaFactory factory;

    public void processar(Cliente cliente, Conversa conversa, Mensagem mensagem) {
        log.info("Processando mensagem de uma conversa inativa. Cliente: {}, Conversa: {}, Mensagem: {}", cliente, conversa, mensagem);

        if (!conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.MENSAGEM_INICIAL)) {
            mensagemOrquestradora.enviarComEspera(cliente.getTelefone(), List.of(
                    mensagemBuilder.getMensagem(TipoMensagem.BOAS_VINDAS, null, null),
                    mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null, null)
            ), mensagem);
        } else {
            try {
                ProcessamentoConversaInativa processamentoConversaInativa = factory.create(mensagem);
                processamentoConversaInativa.processar(cliente, conversa);
            } catch (EscolhaNaoIdentificadoException ex) {

            }
        }

        log.info("Processamento de mensagem de uma conversa inativa concluido com sucesso.");
    }
}
