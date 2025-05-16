package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessoFinalizadoFactory {

    private final List<ProcessoFinalizadoType> processos;

    public ProcessoFinalizadoType create(Mensagem mensagem) {
        return processos.stream()
                .filter(processo ->
                        processo.getTipoMensagem().toString().equals(mensagem.getMensagem())
                ).findFirst()
                .orElseThrow(EscolhaNaoIdentificadoException::new);
    }

}
