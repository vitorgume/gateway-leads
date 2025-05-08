package com.gumeinteligencia.gateway_leads.application.exceptions;

public class ConversaJaExistenteParaClienteException extends RuntimeException {
    public ConversaJaExistenteParaClienteException() {
        super("Conversa ja cadastrada com esse cliente.");
    }
}
