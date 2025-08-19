package com.gumeinteligencia.gateway_leads.infrastructure.exceptions;

public class ApiKeyInvalidaException extends RuntimeException {
    public ApiKeyInvalidaException() {
        super("Chave de api inválida");
    }
}
