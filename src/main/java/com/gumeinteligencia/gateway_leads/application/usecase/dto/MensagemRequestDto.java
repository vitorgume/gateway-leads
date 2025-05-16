package com.gumeinteligencia.gateway_leads.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class MensagemRequestDto {
    private String phone;
    private String message;
}
