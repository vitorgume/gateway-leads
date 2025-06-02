package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.OutroContatoEntity;

public class OutroContatoMapper {
    public static OutroContato paraDomain(OutroContatoEntity entity) {
        return OutroContato.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .telefone(entity.getTelefone())
                .descricao(entity.getDescricao())
                .setor(entity.getSetor())
                .build();
    }
}
