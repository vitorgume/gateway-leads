package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.ConversaGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ClienteMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ConversaMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.ConversaRepository;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ConversaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConversaDataProvider implements ConversaGateway {

    private final ConversaRepository repository;
    private final String MENSAGEM_ERRO_CONSULTAR_POR_CLIENTE = "Erro ao consultar conversa pelo cliente.";
    private final String MENSAMGE_ERRO_SALVAR = "Erro ao salvar conversa.";
    private final String MENSAGEM_ERRO_LISTAR_NAO_FINALIZADOS = "Erro ao listar conversas não finalizadas.";
    private final String MENSAGEM_ERRO_DELETAR = "Erro ao deletar conversa pelo id.";

    @Override
    public Optional<Conversa> consultarPorCliente(Cliente cliente) {
        Optional<ConversaEntity> conversaEntity;

        try {
            conversaEntity = repository.findByCliente(ClienteMapper.paraEntity(cliente));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_CLIENTE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_CLIENTE, ex.getCause());
        }

        return conversaEntity.map(ConversaMapper::paraDomain);
    }

    @Override
    public Conversa salvar(Conversa novaConversa) {
        ConversaEntity conversaEntity = ConversaMapper.paraEntity(novaConversa);

        try {
            conversaEntity = repository.save(conversaEntity);
        } catch (Exception ex) {
            log.error(MENSAMGE_ERRO_SALVAR, ex);
            throw new DataProviderException(MENSAMGE_ERRO_SALVAR, ex.getCause());
        }

        return ConversaMapper.paraDomain(conversaEntity);
    }

    @Override
    public List<Conversa> listarNaoFinalizados() {
        List<ConversaEntity> conversaEntities;

        try {
            conversaEntities = repository.listarNaoFinalizados();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR_NAO_FINALIZADOS, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAR_NAO_FINALIZADOS, ex.getCause());
        }

        return conversaEntities.stream().map(ConversaMapper::paraDomain).toList();
    }

    @Override
    public void deletar(UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_DELETAR, ex);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR, ex.getCause());
        }
    }
}
