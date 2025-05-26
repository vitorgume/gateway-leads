package com.gumeinteligencia.gateway_leads.infrastructure.repository;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    Optional<ClienteEntity> findByTelefoneAndInativoFalse(String telefone);

    @Query(value = """
                SELECT new com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto(
                    cl.nome,
                    cl.telefone,
                    cl.segmento,
                    cl.regiao,
                    co.dataCriacao
                )
                FROM Cliente cl
                JOIN Conversa co ON co.cliente = cl
                WHERE co.dataCriacao >= DATE_SUB(DATE_ADD(NOW(), INTERVAL 3 HOUR), INTERVAL 1 DAY)
                  AND co.dataCriacao <= DATE_ADD(NOW(), INTERVAL 3 HOUR)
                  AND co.vendedor.id = :idVendedor
            """, nativeQuery = true)
    List<RelatorioContatoDto> gerarRelatorio(@Param("idVendedor") Long idVendedor);
}
