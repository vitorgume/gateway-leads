package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.application.usecase.GatewayEnum;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioMapper {

    public static List<RelatorioContatoDto> paraDto(List<Object[]> objects) {
        return objects.stream()
                .map(obj -> new RelatorioContatoDto(
                        (String) obj[0],
                        (String) obj[1],
                        GatewayEnum.gatewaySegmento(String.valueOf(obj[2])),
                        GatewayEnum.gatewayRegiao(String.valueOf(obj[3])),
                        ((Timestamp) obj[4]).toLocalDateTime(),
                        (String) obj[5]
                ))
                .collect(Collectors.toList());
    }
 }
