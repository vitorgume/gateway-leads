package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.ClienteGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ClienteMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.RelatorioMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.ClienteRepository;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.ClienteEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteDataProvider implements ClienteGateway {

    private final ClienteRepository repository;
    private final String MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE = "Erro ao consultar cliente pelo telefone.";
    private final String MENSAGEM_ERRO_SALVAR = "Erro ao salvar cliente.";
    private final String MENSAGEM_ERRO_DELETAR = "Erro ao deletar cliente pelo id.";
    private final String MENSAGEM_ERRO_CONSULTAR_POR_ID = "Erro ao consultar por cliente pelo id.";
    private final String MENSAGEM_ERRO_GERAR_RELATORIO = "Erro ao gerar relatório de contatos.";

    @Override
    public Optional<Cliente> consutlarPorTelfone(String telefone) {
        Optional<ClienteEntity> clienteEntity;

        try {
            clienteEntity = repository.findByTelefoneAndInativoFalse(telefone);
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

    @Override
    public void deletar(UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_DELETAR, ex);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR, ex.getCause());
        }
    }

    @Override
    public Optional<Cliente> consultarPorId(UUID id) {
        Optional<ClienteEntity> clienteEntity;

        try {
            clienteEntity = repository.findById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getCause());
        }

        return clienteEntity.map(ClienteMapper::paraDomain);
    }

    @Override
    public List<RelatorioContatoDto> getRelatorioContato(Long id) {
        List<RelatorioContatoDto> relatorios;

        try {

            relatorios = RelatorioMapper.paraDto(repository.gerarRelatorio(id));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_GERAR_RELATORIO, ex);
            throw new DataProviderException(MENSAGEM_ERRO_GERAR_RELATORIO, ex.getCause());
        }

        return relatorios;
    }
}
