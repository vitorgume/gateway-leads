package com.gumeinteligencia.gateway_leads.infrastructure.repository;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    Optional<ClienteEntity> findByTelefoneAndInativoFalse(String telefone);

    @Query(value = """
                SELECT cl.nome, cl.telefone, cl.segmento, cl.regiao, co.data_criacao
                FROM clientes cl
                INNER JOIN conversas co ON co.cliente_id_cliente = cl.id_cliente
                WHERE co.data_criacao >= DATE_SUB(DATE_ADD(NOW(), INTERVAL 3 HOUR), INTERVAL 1 DAY)
                  AND co.data_criacao <= DATE_ADD(NOW(), INTERVAL 3 HOUR)
                  AND co.vendedor_id_vendedor = :idVendedor
            """, nativeQuery = true)
    List<Object[]> gerarRelatorio(@Param("idVendedor") Long idVendedor);
}
