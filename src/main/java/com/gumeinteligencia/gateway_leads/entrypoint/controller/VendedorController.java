package com.gumeinteligencia.gateway_leads.entrypoint.controller;

import com.gumeinteligencia.gateway_leads.application.usecase.VendedorUseCase;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vendedores")
@RequiredArgsConstructor
public class VendedorController {

    private final VendedorUseCase vendedorUseCase;

    @PostMapping
    public ResponseEntity<Vendedor> cadastrar(@RequestBody Vendedor vendedor) {
        return ResponseEntity.ok(vendedorUseCase.cadastrar(vendedor));
    }
}
