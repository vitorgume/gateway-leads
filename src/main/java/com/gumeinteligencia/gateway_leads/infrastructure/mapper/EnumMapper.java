package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;

import java.util.List;

public class EnumMapper {

    public static List<Integer> paraEnittyMensagemColeta(List<EstadoColeta> mensagensColeta) {
        return mensagensColeta.stream().map(EstadoColeta::getCodigo).toList();
    }

    public static List<EstadoColeta> paraDomainMensagemColeta(List<Integer> mensagensColetaInteger) {
        return mensagensColetaInteger.stream().map(mensagem -> {
            switch (mensagem) {
                case 0 -> {
                    return EstadoColeta.COLETA_SEGMENTO;
                }
                case 1 -> {
                    return EstadoColeta.COLETA_REGIAO;
                }
                case 2 -> {
                    return EstadoColeta.FINALIZA_COLETA;
                }
                default -> {
                    return null;
                }
            }
        }).toList();
    }

    public static List<Integer> paraEntityMensagemDirecionamento(List<MensagemDirecionamento> mensagemDirecionamentos) {
        return mensagemDirecionamentos.stream().map(MensagemDirecionamento::getCodigo).toList();
    }

    public static List<MensagemDirecionamento> paraDomainMensagemDirecionamento(List<Integer> mensagensDirecionamentosInteger) {
        return mensagensDirecionamentosInteger.stream().map(mensagem -> {
            switch (mensagem) {
                case 0 -> {
                    return MensagemDirecionamento.MENSAGEM_INICIAL;
                }
                case 1 -> {
                    return MensagemDirecionamento.ESCOLHA_FINANCEIRO;
                }
                case 2 -> {
                    return MensagemDirecionamento.ESCOLHA_COMERCIAL;
                }
                case 3 -> {
                    return MensagemDirecionamento.ESCOLHA_COMERCIAL_RECONTATO;
                }
                case 4 -> {
                    return MensagemDirecionamento.COLETA_NOME;
                }
                case 5 -> {
                    return MensagemDirecionamento.ESCOLHA_LOGISTICA;
                }
                default -> {
                    return null;
                }
            }


        }).toList();
    }
}
