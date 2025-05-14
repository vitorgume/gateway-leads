package com.gumeinteligencia.gateway_leads.entrypoint.mapper;

import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import com.gumeinteligencia.gateway_leads.entrypoint.controller.dto.MensagemResponseDto;

public class MensagemMapper {
    public static Mensagem paraDomain(MensagemResponseDto dto) {
        return Mensagem.builder()
                .telefone(dto.getPhone())
                .mensagem(dto.getText().getMessage())
                .build();
    }
}
