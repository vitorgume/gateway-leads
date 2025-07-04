package com.gumeinteligencia.gateway_leads.infrastructure.repository;

import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.VendedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendedorRepository extends JpaRepository<VendedorEntity, Long> {
    Optional<VendedorEntity> findByNome(String nome);

    @Query("SELECT v FROM Vendedor v WHERE v.nome <> :excecao")
    List<VendedorEntity> listarComExcecao(String excecao);

    Optional<VendedorEntity> findByTelefone(String telefone);
}
