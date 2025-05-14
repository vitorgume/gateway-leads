package com.gumeinteligencia.gateway_leads.entrypoint.controller.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MensagemResponseDto {
    private String phone;
    private TextoDto text;
}
