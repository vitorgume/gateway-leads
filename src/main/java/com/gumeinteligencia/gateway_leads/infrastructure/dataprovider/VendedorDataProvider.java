package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.VendedorGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.VendedorMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.VendedorRepository;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.VendedorEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class VendedorDataProvider implements VendedorGateway {

    private final VendedorRepository repository;
    private final String MENSAGEM_ERRO_CONSULTAR_VENDEDOR_POR_NOME = "Erro ao consultar vendedor pelo seu nome.";
    private final String MENSAGEM_ERRO_LISTAR = "Erro ao listar todos os vendedores.";
    private final String MENSAGEM_ERRO_LISTAR_SEM_NILZA = "Erro ao listar todos os vendedores exceto a nilza";
    private final String MENSAGEM_ERRO_SALVAR = "Erro ao salvar vendedor.";
    private final String MENSAGEM_ERRO_DELETAR_POR_ID = "Erro ao deletar vendedor pelo id.";
    private final String MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE = "Erro ao consultar vendedor pelo seu telefone.";

    @Override
    public Optional<Vendedor> consultarVendedor(String nome) {
        Optional<VendedorEntity> vendedorEntity;

        try {
            vendedorEntity = repository.findByNome(nome);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_VENDEDOR_POR_NOME, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_VENDEDOR_POR_NOME, ex.getCause());
        }

        return vendedorEntity.map(VendedorMapper::paraDomain);
    }

    @Override
    public List<Vendedor> listar() {
        List<VendedorEntity> vendedorEntities;

        try {
            vendedorEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAR, ex.getCause());
        }
        return vendedorEntities.stream().map(VendedorMapper::paraDomain).toList();
    }

    @Override
    public List<Vendedor> listarSemNilza() {
        List<VendedorEntity> vendedorEntities;

        try {
            vendedorEntities = repository.listarSemNilza();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR_SEM_NILZA, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAR_SEM_NILZA, ex.getCause());
        }

        return vendedorEntities.stream().map(VendedorMapper::paraDomain).toList();
    }

    @Override
    public Vendedor salvar(Vendedor vendedor) {
        VendedorEntity vendedorEntity = VendedorMapper.paraEntity(vendedor);

        try {
            vendedorEntity = repository.save(vendedorEntity);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR, ex.getCause());
        }

        return VendedorMapper.paraDomain(vendedorEntity);
    }

    @Override
    public void deletar(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_DELETAR_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR_POR_ID, ex.getCause());
        }
    }

    @Override
    public Optional<Vendedor> consultarPorTelefone(String telefone) {
        Optional<VendedorEntity> vendedor;

        try {
            vendedor = repository.findByTelefone(telefone);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE, ex.getCause());
        }


        return vendedor.map(VendedorMapper::paraDomain);
    }
}
