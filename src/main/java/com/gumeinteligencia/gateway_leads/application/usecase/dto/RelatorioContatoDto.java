package com.gumeinteligencia.gateway_leads.application.usecase.dto;

import java.time.LocalDateTime;

public class RelatorioContatoDto {
    private String nome;
    private String telefone;
    private String segmento;
    private String regiao;
    private LocalDateTime dataCriacao;

    public RelatorioContatoDto(String nome, String telefone, String segmento, String regiao, LocalDateTime dataCriacao) {
        this.nome = nome;
        this.telefone = telefone;
        this.segmento = segmento;
        this.regiao = regiao;
        this.dataCriacao = dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
