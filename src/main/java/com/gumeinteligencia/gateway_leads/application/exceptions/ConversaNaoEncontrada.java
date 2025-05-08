package com.gumeinteligencia.gateway_leads.application.exceptions;

public class ConversaNaoEncontrada extends RuntimeException {
    public ConversaNaoEncontrada() {
        super("Conversa não encontrada com o cliente específicado.");
    }
}
