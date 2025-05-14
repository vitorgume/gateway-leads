package com.gumeinteligencia.gateway_leads.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ContatoRequestDto {
    private String phone;
    private String contactName;
    private String contactPhone;
}
