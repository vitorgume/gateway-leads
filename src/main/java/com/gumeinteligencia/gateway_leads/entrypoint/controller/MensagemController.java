package com.gumeinteligencia.gateway_leads.entrypoint.controller;

import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.ProcessarMensagemUseCase;
import com.gumeinteligencia.gateway_leads.entrypoint.controller.dto.MensagemDto;
import com.gumeinteligencia.gateway_leads.entrypoint.mapper.MensagemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final ProcessarMensagemUseCase processarMensagemUseCase;

    @PostMapping
    public ResponseEntity<Void> receberMensagem(@RequestBody MensagemDto novaMensagem) {
        processarMensagemUseCase.processarNovaMensagem(MensagemMapper.paraDomain(novaMensagem));
        return ResponseEntity.ok().build();
    }
}
