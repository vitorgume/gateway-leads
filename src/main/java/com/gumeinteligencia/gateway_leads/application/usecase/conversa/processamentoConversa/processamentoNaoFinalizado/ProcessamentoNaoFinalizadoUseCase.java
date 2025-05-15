package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado;

import org.springframework.stereotype.Service;

@Service
public class ProcessamentoNaoFinalizadoUseCase {

    private ProcessoNaoFinalizadoType processoNaoFinalizadoType;

    public ProcessoNaoFinalizadoType setProcessoFinalizadoType(ProcessoNaoFinalizadoType processoNaoFinalizadoType) {
        this.processoNaoFinalizadoType = processoNaoFinalizadoType;
        return this.processoNaoFinalizadoType;
    }

}
