package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessoNaoFinalizadoFactory {

    private final List<ProcessoNaoFinalizadoType> processos;

    public ProcessoNaoFinalizadoType create(Mensagem mensagem) {
        return processos.stream()
                .filter(processo
                        -> processo.getTipoMensage().toString().equals(mensagem.getMensagem())
                ).findFirst()
                .orElseThrow(EscolhaNaoIdentificadoException::new);
    }
}
