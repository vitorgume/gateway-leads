package com.gumeinteligencia.gateway_leads.application.usecase.dto;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
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
    private Segmento segmento;
    private Regiao regiao;
    private LocalDateTime dataCriacao;
    private String nomeVendedor;
}
