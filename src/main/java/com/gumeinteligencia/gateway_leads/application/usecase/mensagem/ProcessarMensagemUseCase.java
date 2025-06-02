package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoConversaExistenteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoNovaConversa;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessarMensagemUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ProcessamentoConversaExistenteUseCase processamentoConversaExistenteUseCase;
    private final ProcessamentoNovaConversa processamentoNovaConversa;
    private final VendedorUseCase vendedorUseCase;
    private final OutroContatoUseCase outroContatoUseCase;


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
        List<String> outrosTelefones = outroContatoUseCase.listar().stream().map(OutroContato::getTelefone).toList();

        if(vendedor.isEmpty()) {
            return !outrosTelefones.contains(telefone);
        }

        return false;
    }
}
