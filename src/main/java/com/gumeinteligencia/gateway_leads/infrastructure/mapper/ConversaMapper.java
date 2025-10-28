package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ConversaEntity;

public class ConversaMapper {

    public static Conversa paraDomain(ConversaEntity entity) {
        Conversa conversaBuild = Conversa.builder()
                .id(entity.getId())
                .cliente(ClienteMapper.paraDomain(entity.getCliente()))
                .dataCriacao(entity.getDataCriacao())
                .mensagemColeta(EnumMapper.paraDomainMensagemColeta(entity.getMensagemColeta()))
                .mensagemDirecionamento(EnumMapper.paraDomainMensagemDirecionamento(entity.getMensagemDirecionamento()))
                .finalizada(entity.getFinalizada())
                .encerrada(entity.getEncerrada())
                .tipoUltimaMensagem(entity.getTipoUltimaMensagem())
                .ultimaMensagem(entity.getUltimaMensagem())
                .inativa(entity.getInativa())
                .inativo(entity.getInativo())
                .ultimaMensagemConversaFinalizada(entity.getUltimaMensagemConversaFinalizada())
                .build();

        if(entity.getVendedor() != null) {
            conversaBuild.setVendedor(VendedorMapper.paraDomain(entity.getVendedor()));
        } else {
            conversaBuild.setVendedor(null);
        }

        if(entity.getTipoUltimaMensagem() == null) {
            conversaBuild.setTipoUltimaMensagem(null);
        } else {
            conversaBuild.setUltimaMensagem(entity.getUltimaMensagem());
        }

        return conversaBuild;
    }

    public static ConversaEntity paraEntity(Conversa domain) {
        ConversaEntity conversaBuild = ConversaEntity.builder()
                .id(domain.getId())
                .cliente(ClienteMapper.paraEntity(domain.getCliente()))
                .dataCriacao(domain.getDataCriacao())
                .mensagemColeta(EnumMapper.paraEntityMensagemColeta(domain.getMensagemColeta()))
                .mensagemDirecionamento(EnumMapper.paraEntityMensagemDirecionamento(domain.getMensagemDirecionamento()))
                .finalizada(domain.getFinalizada())
                .encerrada(domain.getEncerrada())
                .tipoUltimaMensagem(domain.getTipoUltimaMensagem())
                .ultimaMensagem(domain.getUltimaMensagem())
                .inativa(domain.getInativa())
                .inativo(domain.getInativo())
                .ultimaMensagemConversaFinalizada(domain.getUltimaMensagemConversaFinalizada())
                .build();

        if(domain.getVendedor() != null) {
            conversaBuild.setVendedor(VendedorMapper.paraEntity(domain.getVendedor()));
        } else {
            conversaBuild.setVendedor(null);
        }

        if(domain.getTipoUltimaMensagem() == null) {
            conversaBuild.setTipoUltimaMensagem(null);
        } else {
            conversaBuild.setUltimaMensagem(domain.getUltimaMensagem());
        }

        return conversaBuild;
    }
}
