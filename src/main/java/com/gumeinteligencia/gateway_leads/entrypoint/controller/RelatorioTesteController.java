package com.gumeinteligencia.gateway_leads.entrypoint.controller;

import com.gumeinteligencia.gateway_leads.application.usecase.RelatorioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("relatorio-teste")
@RequiredArgsConstructor
public class RelatorioTesteController {

    private final RelatorioUseCase relatorioUseCase;

    @GetMapping
    public ResponseEntity<Void> getRelatorio() {
        relatorioUseCase.enviarRelatorioDiarioVendedores();
        return ResponseEntity.ok().build();
    }
}
