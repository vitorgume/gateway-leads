package com.gumeinteligencia.gateway_leads.application.exceptions;

public class ClienteJaCadastradoException extends RuntimeException {
    public ClienteJaCadastradoException() {
        super("Cliente já cadastrado no sistema com o número específicado.");
    }
}
