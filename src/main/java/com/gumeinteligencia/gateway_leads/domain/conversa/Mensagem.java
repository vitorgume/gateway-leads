package com.gumeinteligencia.gateway_leads.domain.conversa;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class Mensagem {
    private UUID id;
    private String telefone;
    private String mensagem;
}
