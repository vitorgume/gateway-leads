package com.gumeinteligencia.gateway_leads.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class RelatorioContatoDto {
    private String nome;
    private String telefone;
    private String segmento;
    private String regiao;
    private LocalDateTime dataCriacao;
}
