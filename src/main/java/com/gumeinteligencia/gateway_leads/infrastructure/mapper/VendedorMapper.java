package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.VendedorEntity;

public class VendedorMapper {
    public static Vendedor paraDomain(VendedorEntity entity) {
        return Vendedor.builder()
                .id(entity.getId())
                .telefone(entity.getTelefone())
                .nome(entity.getNome())
                .inativo(entity.getInativo())
                .build();
    }

    public static VendedorEntity paraEntity(Vendedor domain) {
        return VendedorEntity.builder()
                .id(domain.getId())
                .telefone(domain.getTelefone())
                .nome(domain.getNome())
                .inativo(domain.getInativo())
                .build();
    }
}
