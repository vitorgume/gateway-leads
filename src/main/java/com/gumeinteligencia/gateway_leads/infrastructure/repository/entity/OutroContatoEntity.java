package com.gumeinteligencia.gateway_leads.infrastructure.repository.entity;

import com.gumeinteligencia.gateway_leads.domain.outroContato.Setor;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "OutroContato")
@Table(name = "outros_contatos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OutroContatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_outro_contato")
    private Long id;
    private String nome;
    private String telefone;
    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    private Setor setor;
}
