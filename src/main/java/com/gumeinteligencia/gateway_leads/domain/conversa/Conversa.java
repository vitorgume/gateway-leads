package com.gumeinteligencia.gateway_leads.domain.conversa;


import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Conversa {
    private UUID id;
    private Cliente cliente;
    private Vendedor vendedor;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaMensagem;
    private MensagemColeta mensagemColeta;
    private MensagemDirecionamento mensagemDirecionamento;
    private Boolean finalizada;
    private Boolean encerrada;
    private TipoMensagem ultimaMensagem;
}
