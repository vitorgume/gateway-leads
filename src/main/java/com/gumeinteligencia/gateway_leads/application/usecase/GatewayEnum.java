package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;

public class GatewayEnum {
    public static Segmento gatewaySegmento(String mensagem) {
        String mensagemFormatada = mensagem.toLowerCase();

        return switch (mensagemFormatada) {
            case "1" -> Segmento.MEDICINA_SAUDE;
            case "2" -> Segmento.BOUTIQUE_LOJAS;
            case "3" -> Segmento.ENGENHARIA_ARQUITETURA;
            case "4" -> Segmento.ALIMENTOS;
            case "5" -> Segmento.CELULARES;
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
