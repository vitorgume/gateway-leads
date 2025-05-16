package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.MensagemRequestDto;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;

public class MensagemMapper {

    public static MensagemRequestDto paraRequestDto(Mensagem mensagem) {
        return MensagemRequestDto.builder()
                .message(mensagem.getMensagem())
                .phone(mensagem.getTelefone())
                .build();
    }
}
