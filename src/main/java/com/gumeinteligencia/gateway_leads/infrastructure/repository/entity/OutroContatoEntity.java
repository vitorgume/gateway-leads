package com.gumeinteligencia.gateway_leads.infrastructure.repository.entity;

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
    private Long id;
    private String nome;
    private String telefone;
    private String descricao;
}
