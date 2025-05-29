package com.gumeinteligencia.gateway_leads.application.exceptions;

public class OutroContatoNaoEncontradoException extends RuntimeException {
    public OutroContatoNaoEncontradoException() {
        super("Outro contato não encontrado pelo seu nome");
    }
}
