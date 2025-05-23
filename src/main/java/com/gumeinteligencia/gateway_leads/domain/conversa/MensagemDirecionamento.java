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
    private boolean escolhaFinanceiro;
    private boolean escolhaComercial;
    private boolean escolhaComercialRecontato;
    private boolean coletaNome;
    private boolean escolhaLogistica;


    public MensagemDirecionamento() {
        this.escolhaFinanceiro = false;
        this.escolhaComercial = false;
        this.mensagemInicial = false;
        this.coletaNome = false;
        this.escolhaComercialRecontato = false;
        this.escolhaLogistica = false;
    }
}
