package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

import java.util.List;

public interface JanelaInicial {
    void adicionarSeNaoExiste(String telefone);
    void armazenarMensagens(String telefone, List<String> telefones, Mensagem ultima);
    void removerBloqueio(String telefone);
}
