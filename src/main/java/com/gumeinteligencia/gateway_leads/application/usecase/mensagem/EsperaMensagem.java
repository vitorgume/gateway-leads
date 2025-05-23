package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class EsperaMensagem {
    private List<String> mensagensParaEnviar = new ArrayList<>();
    private Mensagem ultimaMensagemRecebida;
    private Conversa conversa;
}
