package com.gumeinteligencia.gateway_leads.domain.mensagem;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Mensagem {
    private UUID id;
    private String telefone;
    private String mensagem;
}
