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
    private boolean opcaoEscolha;


    public MensagemDirecionamento() {
        this.opcaoEscolha = false;
    }
}
