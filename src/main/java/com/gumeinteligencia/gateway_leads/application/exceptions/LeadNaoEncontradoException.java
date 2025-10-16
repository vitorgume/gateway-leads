package com.gumeinteligencia.gateway_leads.application.exceptions;

public class LeadNaoEncontradoException extends RuntimeException {

    public LeadNaoEncontradoException() {
        super("Lead não encontrado.");
    }
}
