package com.gumeinteligencia.gateway_leads.domain.conversa;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class MensagemDirecionamento {
    private boolean mensagemInicial;
    private String opcaoEscolha;
    private boolean coletaNome;


    public MensagemDirecionamento() {
        this.opcaoEscolha = "";
        this.mensagemInicial = false;
        this.coletaNome = false;
    }
}
