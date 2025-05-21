package com.gumeinteligencia.gateway_leads.application.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado com o id especifícado");
    }
}
