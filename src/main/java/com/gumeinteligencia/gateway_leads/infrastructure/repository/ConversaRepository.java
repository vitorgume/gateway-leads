package com.gumeinteligencia.gateway_leads.infrastructure.repository;

import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ClienteEntity;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ConversaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversaRepository extends JpaRepository<ConversaEntity, UUID> {
    Optional<ConversaEntity> findByCliente(ClienteEntity clienteEntity);

    @Query("SELECT c FROM Conversa c WHERE c.finalizada = false")
    List<ConversaEntity> listarNaoFinalizados();

    void deleteByCliente_Telefone(String telefone);
}
