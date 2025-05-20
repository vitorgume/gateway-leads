package com.gumeinteligencia.gateway_leads.entrypoint.controller;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("conversas")
@RequiredArgsConstructor
public class ConversaController {

    private final ConversaUseCase conversaUseCase;

    @DeleteMapping("/{telefone}")
    public ResponseEntity<Void> deletar(@PathVariable String telefone) {
        conversaUseCase.deletarPorTelefoneCliente(telefone);
        return ResponseEntity.noContent().build();
    }

}
