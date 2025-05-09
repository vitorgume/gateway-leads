package com.gumeinteligencia.gateway_leads.application.exceptions;

public class VendedorNaoEncontradoException extends RuntimeException {
    public VendedorNaoEncontradoException() {
        super("Vendedor não encontrado pelo seu nome.");
    }
}
