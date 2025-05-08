package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.Conversa;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ConversaEntity;

public class ConversaMapper {

    public static Conversa paraDomain(ConversaEntity entity) {
        return Conversa.builder()
                .id(entity.getId())
                .cliente(ClienteMapper.paraDomain(entity.getCliente()))
                .vendedor(VendedorMapper.paraDomain(entity.getVendedor()))
                .dataCriacao(entity.getDataCriacao())
                .mensagemColeta(entity.getMensagemColeta())
                .finalizada(entity.getFinalizada())
                .build();
    }

    public static ConversaEntity paraEntity(Conversa domain) {
        return ConversaEntity.builder()
                .id(domain.getId())
                .cliente(ClienteMapper.paraEntity(domain.getCliente()))
                .vendedor(VendedorMapper.paraEntity(domain.getVendedor()))
                .dataCriacao(domain.getDataCriacao())
                .mensagemColeta(domain.getMensagemColeta())
                .finalizada(domain.getFinalizada())
                .build();
    }
}
