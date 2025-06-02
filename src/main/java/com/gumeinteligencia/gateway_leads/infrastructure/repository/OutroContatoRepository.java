package com.gumeinteligencia.gateway_leads.infrastructure.repository;

import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.OutroContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutroContatoRepository extends JpaRepository<OutroContatoEntity, Long> {
    Optional<OutroContatoEntity> findByNome(String nome);
}
