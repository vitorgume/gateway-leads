package com.gumeinteligencia.gateway_leads.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Endereco {
    private String municipio;
    private String UF;
}
