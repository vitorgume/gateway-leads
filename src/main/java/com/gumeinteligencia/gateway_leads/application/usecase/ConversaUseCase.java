package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.ConversaJaExistenteParaClienteException;
import com.gumeinteligencia.gateway_leads.application.exceptions.ConversaNaoEncontrada;
import com.gumeinteligencia.gateway_leads.application.gateways.ConversaGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversaUseCase {

    private final ConversaGateway gateway;


    public Conversa consultarPorCliente(Cliente cliente) {
        log.info("Consultando coversa pelo cliente. Cliente: {}", cliente);

        Optional<Conversa> conversaOptional = gateway.consultarPorCliente(cliente);

        if(conversaOptional.isEmpty()) {
            throw new ConversaNaoEncontrada();
        }

        Conversa conversa = conversaOptional.get();

        log.info("Conversa consultada com sucesso. Conversa: {}", conversa);

        return conversa;
    }

    public Conversa criar(Cliente cliente) {
        Optional<Conversa> conversaOptional = gateway.consultarPorCliente(cliente);

        conversaOptional
                .ifPresent(
                        conversa -> {throw new ConversaJaExistenteParaClienteException();}
                );

        Conversa novaConversa = Conversa.builder()
                .dataCriacao(LocalDateTime.now())
                .cliente(cliente)
                .mensagemColeta(new MensagemColeta())
                .finalizada(false)
                .mensagemDirecionamento(new MensagemDirecionamento())
                .build();

        return gateway.salvar(novaConversa);
    }

    public Conversa salvar(Conversa conversa) {
        return gateway.salvar(conversa);
    }

    public List<Conversa> listarNaoFinalizados() {
        return gateway.listarNaoFinalizados();
    }

    public void encerrar(UUID id) {
        Conversa conversa = consultarPorId(id);

        conversa.setEncerrada(true);

        gateway.salvar(conversa);
    }

    private Conversa consultarPorId(UUID id) {
        Optional<Conversa> conversa = gateway.consultarPorId(id);

        if(conversa.isEmpty()) {
            throw new ConversaNaoEncontrada();
        }

        return conversa.get();
    }
}
