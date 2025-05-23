package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MensagemFactory {

    private final List<MensagemType> mensagens;

    public MensagemType create(TipoMensagem tipo) {
        return mensagens.stream()
                .filter(mensagem -> mensagem.getTipoMensagem().equals(tipo.getCodigo()))
                .findFirst()
                .orElseThrow(EscolhaNaoIdentificadoException::new);
    }
}
