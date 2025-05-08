package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.ConversaGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Conversa;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ClienteMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ConversaMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ConversaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConversaDataProvider implements ConversaGateway {

    private final ConversaRepository repository;
    private final String MENSAGEM_ERRO_CONSULTAR_POR_CLIENTE = "Erro ao consultar conversa pelo cliente.";
    private final String MENSAMGE_ERRO_SALVAR = "Erro ao salvar conversa.";

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
    public void salvar(Conversa novaConversa) {
        ConversaEntity conversaEntity = ConversaMapper.paraEntity(novaConversa);

        try {
            conversaEntity = repository.save(conversaEntity);
        } catch (Exception ex) {
            log.error(MENSAMGE_ERRO_SALVAR, ex);
            throw new DataProviderException(MENSAMGE_ERRO_SALVAR, ex.getCause());
        }
    }
}
