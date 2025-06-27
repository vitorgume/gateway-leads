package com.gumeinteligencia.gateway_leads.application.gateways;

public interface CriptografiaGateway {
    String criptografar(String senha);
    boolean validarSenha(String senha, String senhaUsuario);
}
