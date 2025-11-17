package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider;

import com.gumeinteligencia.gateway_leads.application.gateways.OutroContatoGateway;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.OutroContatoMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.OutroContatoRepository;
import com.gumeinteligencia.gateway_leads.infrastructure.repository.entity.OutroContatoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutroContatoDataProvider implements OutroContatoGateway {

    private final OutroContatoRepository repository;
    private final String MENSAGEM_ERRO_CONSULTAR_POR_NOME_OUTRO_CONTATO = "Erro ao consultar por nome outro contato.";
    private final String MENSAGEM_ERRO_LISTAR_OUTROS_CONTATOS = "Erro ao listar outros contatos.";
    private final String MENSAGEM_ERRO_VERIFICACAO_TELEFONE_EXISTE = "Erro ao verificar telefone outros contatos.";

    @Override
    public Optional<OutroContato> consultarPorNome(String nome) {
        Optional<OutroContatoEntity> outroContato;

        try {
            outroContato = repository.findByNome(nome);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_NOME_OUTRO_CONTATO, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_NOME_OUTRO_CONTATO, ex.getCause());
        }

        return outroContato.map(OutroContatoMapper::paraDomain);
    }

    @Override
    public List<OutroContato> listar() {
        List<OutroContatoEntity> outrosContatos;

        try {
            outrosContatos = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR_OUTROS_CONTATOS, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAR_OUTROS_CONTATOS, ex.getCause());
        }

        return outrosContatos.stream().map(OutroContatoMapper::paraDomain).toList();
    }

    @Override
    public boolean existeTelefone(String telefone) {
        try {
            return repository.existsByTelefone(telefone);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_VERIFICACAO_TELEFONE_EXISTE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_VERIFICACAO_TELEFONE_EXISTE, ex.getCause());
        }
    }
}
