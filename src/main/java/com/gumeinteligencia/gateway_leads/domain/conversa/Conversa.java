package com.gumeinteligencia.gateway_leads.domain.conversa;


import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime ultimaMensagem;
    private LocalDateTime ultimaMensagemConversaFinalizada;
    private List<EstadoColeta> mensagemColeta;
    private List<MensagemDirecionamento> mensagemDirecionamento;
    private Boolean finalizada;
    private Boolean encerrada;
    private TipoInativo inativo;
    private TipoMensagem tipoUltimaMensagem;
}
