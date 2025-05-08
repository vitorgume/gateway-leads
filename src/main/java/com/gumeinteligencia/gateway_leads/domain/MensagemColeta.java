package com.gumeinteligencia.gateway_leads.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Embeddable
@Getter
@Setter
@ToString
public class MensagemColeta {
    private boolean coletaNome;
    private boolean coletaSegmento;
    private boolean coletaEndereco;

    public MensagemColeta() {
        this.coletaEndereco = false;
        this.coletaSegmento = false;
        this.coletaNome = false;
    }
}
