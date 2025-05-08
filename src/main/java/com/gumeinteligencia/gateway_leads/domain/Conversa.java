package com.gumeinteligencia.gateway_leads.domain;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Conversa {
    private UUID id;
    private Cliente cliente;
    private Vendedor vendedor;
    private LocalDateTime dataCriacao;
    private MensagemColeta mensagemColeta;
    private Boolean finalizada;
}
