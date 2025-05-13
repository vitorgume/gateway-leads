package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ClienteEntity;

public class ClienteMapper {
    public static Cliente paraDomain(ClienteEntity entity) {
        return Cliente.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .telefone(entity.getTelefone())
                .regiao(entity.getRegiao())
                .segmento(entity.getSegmento())
                .inativo(entity.getInativo())
                .build();
    }

    public static ClienteEntity paraEntity(Cliente domain) {
        return ClienteEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .telefone(domain.getTelefone())
                .regiao(domain.getRegiao())
                .segmento(domain.getSegmento())
                .inativo(domain.getInativo())
                .build();
    }
}


