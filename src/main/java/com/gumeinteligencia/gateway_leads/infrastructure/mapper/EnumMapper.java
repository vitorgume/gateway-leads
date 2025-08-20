package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnumMapper {

    public static List<Integer> paraEntityMensagemColeta(List<EstadoColeta> mensagensColeta) {
        if (mensagensColeta == null) return new ArrayList<>();
        return mensagensColeta.stream()
                .map(EstadoColeta::getCodigo)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<EstadoColeta> paraDomainMensagemColeta(List<Integer> codigos) {
        if (codigos == null) return new ArrayList<>();
        return codigos.stream()
                .map(EnumMapper::mapEstadoColeta)
                .flatMap(Optional::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Optional<EstadoColeta> mapEstadoColeta(Integer code) {
        if (code == null) return Optional.empty();
        return switch (code) {
            case 0 -> Optional.of(EstadoColeta.COLETA_SEGMENTO);
            case 1 -> Optional.of(EstadoColeta.COLETA_REGIAO);
            case 2 -> Optional.of(EstadoColeta.FINALIZA_COLETA);
            default -> Optional.empty();
        };
    }

    public static List<Integer> paraEntityMensagemDirecionamento(List<MensagemDirecionamento> lista) {
        if (lista == null) return new ArrayList<>();
        return lista.stream()
                .map(MensagemDirecionamento::getCodigo)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<MensagemDirecionamento> paraDomainMensagemDirecionamento(List<Integer> codigos) {
        if (codigos == null) return new ArrayList<>();
        return codigos.stream()
                .map(EnumMapper::mapMensagemDirecionamento)
                .flatMap(Optional::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Optional<MensagemDirecionamento> mapMensagemDirecionamento(Integer code) {
        if (code == null) return Optional.empty();
        return switch (code) {
            case 0 -> Optional.of(MensagemDirecionamento.MENSAGEM_INICIAL);
            case 1 -> Optional.of(MensagemDirecionamento.ESCOLHA_FINANCEIRO);
            case 2 -> Optional.of(MensagemDirecionamento.ESCOLHA_COMERCIAL);
            case 3 -> Optional.of(MensagemDirecionamento.ESCOLHA_COMERCIAL_RECONTATO);
            case 4 -> Optional.of(MensagemDirecionamento.COLETA_NOME);
            case 5 -> Optional.of(MensagemDirecionamento.ESCOLHA_LOGISTICA);
            default -> Optional.empty();
        };
    }
}
