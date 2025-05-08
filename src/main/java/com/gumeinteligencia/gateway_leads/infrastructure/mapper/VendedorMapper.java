package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.VendedorEntity;

public class VendedorMapper {
    public static Vendedor paraDomain(VendedorEntity entity) {
        return Vendedor.builder()
                .id(entity.getId())
                .telefone(entity.getTelefone())
                .nome(entity.getNome())
                .build();
    }

    public static VendedorEntity paraEntity(Vendedor entity) {
        return VendedorEntity.builder()
                .id(entity.getId())
                .telefone(entity.getTelefone())
                .nome(entity.getNome())
                .build();
    }
}
