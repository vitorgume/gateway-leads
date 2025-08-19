package com.gumeinteligencia.gateway_leads.infrastructure.repository.entity;

import com.gumeinteligencia.gateway_leads.domain.Canal;
import com.gumeinteligencia.gateway_leads.domain.Regiao;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Cliente")
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cliente")
    private UUID id;
    private String nome;
    private String telefone;

    @Enumerated(EnumType.ORDINAL)
    private Regiao regiao;

    @Enumerated(EnumType.ORDINAL)
    private Segmento segmento;

    private boolean inativo;

    @Enumerated(EnumType.ORDINAL)
    private Canal canal;
}
