package com.gumeinteligencia.gateway_leads.infrastructure.repository;

import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    @Query("SELECT c FROM Cliente c WHERE c.telefone = :telefone AND c.inativo = false")
    Optional<ClienteEntity> findByTelefone(String telefone);
}
