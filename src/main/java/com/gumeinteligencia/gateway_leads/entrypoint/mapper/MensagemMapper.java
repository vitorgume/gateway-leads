package com.gumeinteligencia.gateway_leads.entrypoint.mapper;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.entrypoint.controller.dto.MensagemDto;

public class MensagemMapper {
    public static Mensagem paraDomain(MensagemDto dto) {
        return Mensagem.builder()
                .telefone(dto.getPhone())
                .mensagem(dto.getText().getMessage())
                .build();
    }
}
