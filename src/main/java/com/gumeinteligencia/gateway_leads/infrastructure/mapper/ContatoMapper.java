package com.gumeinteligencia.gateway_leads.infrastructure.mapper;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.ContatoRequestDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;

public class ContatoMapper {

    public static ContatoRequestDto paraRequestDto(Cliente cliente, String telefoneDestinatario) {
        if(cliente.getNome() == null) {
            return ContatoRequestDto.builder()
                    .phone(telefoneDestinatario)
                    .contactName("Nome não informado")
                    .contactPhone(cliente.getTelefone())
                    .build();
        } else {
            return ContatoRequestDto.builder()
                    .phone(telefoneDestinatario)
                    .contactName(cliente.getNome())
                    .contactPhone(cliente.getTelefone())
                    .build();
        }
    }
}
