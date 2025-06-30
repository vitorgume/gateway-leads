package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoInativo;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.ProcessoFinalizadoType;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessamentoConversaInativaFactory {

    private final List<ProcessamentoConversaInativa> processos;

    public ProcessamentoConversaInativa create(Mensagem mensagem) {
        return processos.stream()
                .filter(processo ->
                        processo.mensagem().equals(mensagem.getMensagem())
                ).findFirst()
                .orElseThrow(EscolhaNaoIdentificadoException::new);
    }

}
