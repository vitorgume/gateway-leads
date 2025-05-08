package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.ClienteGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ClienteMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.ClienteRepository;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ClienteEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteDataProvider implements ClienteGateway {

    private final ClienteRepository repository;
    private final String MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE = "Erro ao consultar cliente pelo telefone.";
    private final String MENSAGEM_ERRO_SALVAR = "Erro ao salvar cliente.";

    @Override
    public Optional<Cliente> consutlarPorTelfone(String telefone) {
        Optional<ClienteEntity> clienteEntity;

        try {
            clienteEntity = repository.findByTelefone(telefone);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE, ex.getCause());
        }

        return clienteEntity.map(ClienteMapper::paraDomain);
    }

    @Override
    public Cliente salvar(Cliente novoCliente) {
        ClienteEntity clienteEntity = ClienteMapper.paraEntity(novoCliente);

        try {
            clienteEntity = repository.save(clienteEntity);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR, ex.getCause());
        }

        return ClienteMapper.paraDomain(clienteEntity);
    }
}
