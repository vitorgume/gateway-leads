package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JanelaInicialDeBloqueio {
    private final Map<String, Long> bloqueioInicial = new ConcurrentHashMap<>();
    private static final long TEMPO_ESPERA_MILLIS = 15_000;

    public boolean deveAguardar(String telefone) {
        if (!bloqueioInicial.containsKey(telefone)) return false;
        long tempoInicio = bloqueioInicial.get(telefone);
        return System.currentTimeMillis() - tempoInicio < TEMPO_ESPERA_MILLIS;
    }

    public void iniciarBloqueio(String telefone) {
        bloqueioInicial.put(telefone, System.currentTimeMillis());
    }

    public void removerBloqueio(String telefone) {
        bloqueioInicial.remove(telefone);
    }
}
