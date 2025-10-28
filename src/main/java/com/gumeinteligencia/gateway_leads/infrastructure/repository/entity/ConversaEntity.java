package com.gumeinteligencia.gateway_leads.infrastructure.repository.entity;

import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import com.gumeinteligencia.gateway_leads.domain.conversa.TipoInativo;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "Conversa")
@Table(name = "conversas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ConversaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_conversa")
    private UUID id;

    @OneToOne
    private ClienteEntity cliente;

    @ManyToOne
    private VendedorEntity vendedor;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    private List<Integer> mensagemColeta;

    private List<Integer> mensagemDirecionamento;

    private LocalDateTime ultimaMensagem;

    private LocalDateTime ultimaMensagemConversaFinalizada;

    private Boolean finalizada;

    private Boolean encerrada;

    private Boolean inativa;

    @Enumerated(EnumType.ORDINAL)
    private TipoInativo inativo;

    @Enumerated(EnumType.ORDINAL)
    private TipoMensagem tipoUltimaMensagem;
}
