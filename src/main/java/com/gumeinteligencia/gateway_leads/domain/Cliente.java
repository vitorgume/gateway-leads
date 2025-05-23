package com.gumeinteligencia.gateway_leads.domain;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Cliente {
    private UUID id;
    private String nome;
    private String telefone;
    private Regiao regiao;
    private Segmento segmento;
    private boolean inativo;
}
