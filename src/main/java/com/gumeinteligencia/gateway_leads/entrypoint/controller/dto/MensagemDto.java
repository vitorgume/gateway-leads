package com.gumeinteligencia.gateway_leads.entrypoint.controller.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MensagemDto {
    private String phone;
    private String message;
}
