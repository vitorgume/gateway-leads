package com.gumeinteligencia.gateway_leads.infrastructure.repository.entity;

import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Embedded
    private MensagemColeta mensagemColeta;

    @Embedded
    private MensagemDirecionamento mensagemDirecionamento;

    private LocalDateTime ultimaMensagem;

    private Boolean finalizada;

    private Boolean encerrada;
}
