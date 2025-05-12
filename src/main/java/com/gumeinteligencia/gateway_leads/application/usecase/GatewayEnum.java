package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;

public class GatewayEnum {
    public static Segmento gatewaySegmento(String mensagem) {
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

    public static Regiao gatewayRegiao(String mensagem) {
        String mensagemFormatada = mensagem.toLowerCase();

        return switch (mensagemFormatada) {
            case "1" -> Regiao.MARINGA;
            case "2" -> Regiao.REGIAO_MARINGA;
            default -> Regiao.OUTRA;
        };
    }
}
