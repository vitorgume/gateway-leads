package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Segmento;

public class GatewaySegmento {
    public static Segmento gateway(String mensagem) {
        String mensagemFormatada = mensagem.toLowerCase();

        return switch (mensagemFormatada) {
            case "1" -> Segmento.SAUDE;
            case "2" -> Segmento.CELULAR;
            case "3" -> Segmento.ARQUITETURA;
            case "4" -> Segmento.ENGENHARIA;
            case "5" -> Segmento.VAREJO;
            case "6" -> Segmento.INDUSTRIA;
            case "7" -> Segmento.ALIMENTOS;
            default -> Segmento.OUTROS;
        };
    }
}
