package com.gumeinteligencia.gateway_leads.domain.conversa;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Embeddable
@Getter
@Setter
@ToString
public class MensagemColeta {
    private boolean coletaSegmento;
    private boolean coletaRegiao;

    public MensagemColeta() {
        this.coletaRegiao = false;
        this.coletaSegmento = false;
    }
}
