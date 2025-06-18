package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial.MensagemOrquestradora;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessamentoNovaConversa {

    private final MensagemOrquestradora mensagemOrquestradora;
    private final MensagemBuilder mensagemBuilder;

    public void iniciarNovaConversa(Mensagem mensagem) {
        log.info("Processando início de uma conversa. Mensagem: {}", mensagem);

        mensagemOrquestradora.enviarComEspera(mensagem.getTelefone(), List.of(
                mensagemBuilder.getMensagem(TipoMensagem.BOAS_VINDAS, null, null),
                mensagemBuilder.getMensagem(TipoMensagem.COLETA_NOME, null, null)
        ), mensagem);

        log.info("Conversa iniciada com sucesso.");
    }
}
