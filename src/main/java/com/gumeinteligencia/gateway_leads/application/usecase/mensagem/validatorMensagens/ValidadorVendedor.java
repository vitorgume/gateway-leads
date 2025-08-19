package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.validatorMensagens;

import com.gumeinteligencia.gateway_leads.application.usecase.vendedor.VendedorUseCase;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidadorVendedor implements MensagemValidator {

    private final VendedorUseCase vendedorUseCase;

    @Override
    public boolean deveIgnorar(Mensagem mensagem) {
        return vendedorUseCase.consultarPorTelefone(mensagem.getTelefone()).isPresent();
    }
}
