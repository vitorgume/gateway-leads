package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoConversaExistenteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoNovaConversa;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProcessarMensagemUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ProcessamentoConversaExistenteUseCase processamentoConversaExistenteUseCase;
    private final ProcessamentoNovaConversa processamentoNovaConversa;
    private final VendedorUseCase vendedorUseCase;
    @Value("${neoprint.financeiro.telefone}")
    private final String financeiroTelefone;

    @Value("${neoprint.logistica.telefone}")
    private final String logisticaTelefone;

    @Value("${neoprint.gerencia.telefone}")
    private final String gerenciaTelefone;

    public ProcessarMensagemUseCase(
            ClienteUseCase clienteUseCase,
            ProcessamentoConversaExistenteUseCase processamentoConversaExistenteUseCase,
            ProcessamentoNovaConversa processamentoNovaConversa,
            VendedorUseCase vendedorUseCase,
            @Value("${neoprint.financeiro.telefone}") String financeiroTelefone,
            @Value("${neoprint.logistica.telefone}") String logisticaTelefone,
            @Value("${neoprint.gerencia.telefone}") String gerenciaTelefone
    ) {
        this.clienteUseCase = clienteUseCase;
        this.processamentoConversaExistenteUseCase = processamentoConversaExistenteUseCase;
        this.processamentoNovaConversa = processamentoNovaConversa;
        this.vendedorUseCase = vendedorUseCase;
        this.financeiroTelefone = financeiroTelefone;
        this.logisticaTelefone = logisticaTelefone;
        this.gerenciaTelefone = gerenciaTelefone;
    }

    public void processarNovaMensagem(Mensagem mensagem) {
        log.info("Processando nova mensagem. Mensagem: {}", mensagem);

        if(validaTelefoneVendedores(mensagem.getTelefone())) {
            clienteUseCase
                    .consultarPorTelefone(mensagem.getTelefone())
                    .ifPresentOrElse(
                            cliente -> processamentoConversaExistenteUseCase.processarConversaExistente(cliente, mensagem),
                            () -> processamentoNovaConversa.iniciarNovaConversa(mensagem)
                    );
        }

        log.info("Mensagem processada com sucesso.");
    }

    private boolean validaTelefoneVendedores(String telefone) {
        Optional<Vendedor> vendedor = vendedorUseCase.consultarPorTelefone(telefone);
        List<String> outrosTelefones = new ArrayList<>(Arrays.asList(financeiroTelefone, logisticaTelefone, gerenciaTelefone));

        if(vendedor.isEmpty()) {
            return !outrosTelefones.contains(telefone);
        }

        return false;
    }
}
