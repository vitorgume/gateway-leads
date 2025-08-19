package com.gumeinteligencia.gateway_leads.application.usecase.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RelatorioOnlineDto {
    private String data;
    private String cliente;
    private String telefone;
    private String vendedor;
}
