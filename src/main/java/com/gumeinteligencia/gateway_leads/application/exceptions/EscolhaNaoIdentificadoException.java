package com.gumeinteligencia.gateway_leads.application.exceptions;

public class EscolhaNaoIdentificadoException extends RuntimeException {
    public EscolhaNaoIdentificadoException() {
        super("Escolha do usuário não identificada.");
    }
}
