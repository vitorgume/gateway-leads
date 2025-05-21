package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ConversaEntity;

public class ConversaMapper {

    public static Conversa paraDomain(ConversaEntity entity) {
        Conversa conversaBuild = Conversa.builder()
                .id(entity.getId())
                .cliente(ClienteMapper.paraDomain(entity.getCliente()))
                .dataCriacao(entity.getDataCriacao())
                .mensagemColeta(entity.getMensagemColeta())
                .mensagemDirecionamento(entity.getMensagemDirecionamento())
                .finalizada(entity.getFinalizada())
                .encerrada(entity.getEncerrada())
                .ultimaMensagem(entity.getUltimaMensagem())
                .build();

        if(entity.getVendedor() != null) {
            conversaBuild.setVendedor(VendedorMapper.paraDomain(entity.getVendedor()));
        } else {
            conversaBuild.setVendedor(null);
        }

        if(entity.getUltimaMensagem() == null) {
            conversaBuild.setUltimaMensagem(null);
        } else {
            conversaBuild.setDataUltimaMensagem(entity.getDataUltimaMensagem());
        }

        return conversaBuild;
    }

    public static ConversaEntity paraEntity(Conversa domain) {
        ConversaEntity conversaBuild = ConversaEntity.builder()
                .id(domain.getId())
                .cliente(ClienteMapper.paraEntity(domain.getCliente()))
                .dataCriacao(domain.getDataCriacao())
                .mensagemColeta(domain.getMensagemColeta())
                .mensagemDirecionamento(domain.getMensagemDirecionamento())
                .finalizada(domain.getFinalizada())
                .encerrada(domain.getEncerrada())
                .ultimaMensagem(domain.getUltimaMensagem())
                .build();

        if(domain.getVendedor() != null) {
            conversaBuild.setVendedor(VendedorMapper.paraEntity(domain.getVendedor()));
        } else {
            conversaBuild.setVendedor(null);
        }

        if(domain.getUltimaMensagem() == null) {
            conversaBuild.setUltimaMensagem(null);
        } else {
            conversaBuild.setDataUltimaMensagem(domain.getDataUltimaMensagem());
        }

        return conversaBuild;
    }
}
