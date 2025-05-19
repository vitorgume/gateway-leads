package com.gumeinteligencia.gateway_leads.application.usecase.dto;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MensagemRequestDto {
    private String phone;
    private String message;
}
