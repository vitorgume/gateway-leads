package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Segmento;

public class GatewaySegmento {
    public static Segmento gateway(String mensagem) {
        String mensagemFormatada = mensagem.toLowerCase();

        return switch (mensagemFormatada) {
            case "saude" -> Segmento.SAUDE;
            case "arquitetura" -> Segmento.ARQUITETURA;
            case "engenharia" -> Segmento.ENGENHARIA;
            case "varejo" -> Segmento.VAREJO;
            case "industria" -> Segmento.INDUSTRIA;
            case "alimentos" -> Segmento.ALIMENTOS;
            case "celular" -> Segmento.CELULAR;
            default -> Segmento.OUTROS;
        };
    }
}
