package com.gumeinteligencia.gateway_leads.entrypoint.mapper;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.entrypoint.controller.dto.MensagemDto;

public class MensagemMapper {
    public static Mensagem paraDomain(MensagemDto dto) {
        if(dto.getText() != null && dto.getText().getMessage() != null) {
            return Mensagem.builder()
                    .telefone(dto.getPhone())
                    .mensagem(dto.getText().getMessage())
                    .build();
        } else if (dto.getImage() != null && dto.getImage().getCaption() != null) {
            return Mensagem.builder()
                    .telefone(dto.getPhone())
                    .mensagem(dto.getImage().getCaption())
                    .build();
        }

        return Mensagem.builder()
                .telefone(dto.getPhone())
                .mensagem("")
                .build();
    }
}

