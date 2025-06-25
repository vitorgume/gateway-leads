package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.conversaExistente;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.ProcessamentoConversaInativaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.DirecionamentoComercial;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.ProcessoFinalizadoFactory;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.ProcessoFinalizadoType;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessaColeta;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessoNaoFinalizadoFactory;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessoNaoFinalizadoType;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial.MensagemOrquestradora;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class ProcessarConversaExistenteDev implements ConversaExistente {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;
    private final ProcessoNaoFinalizadoFactory processoNaoFinalizadoFactory;
    private final ProcessoFinalizadoFactory processoFinalizadoFactory;
    private final MensagemBuilder mensagemBuilder;
    private final MensagemOrquestradora mensagemOrquestradora;
    private final ProcessamentoConversaInativaUseCase processamentoConversaInativaUseCase;

    @Override
    public void processarConversaNaoFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando mensagem de uma conversa não finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
        if (!conversa.getMensagemDirecionamento().isColetaNome()) {
            cliente.setNome(mensagem.getMensagem());
            conversa.getMensagemDirecionamento().setColetaNome(true);
            clienteUseCase.salvar(cliente);
            conversa.setTipoUltimaMensagem(TipoMensagem.DIRECIONAR_SETOR);
            conversaUseCase.salvar(conversa);
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null, null), cliente.getTelefone(), conversa);
        } else {

            try {
                ProcessoNaoFinalizadoType strategy = conversa.getMensagemDirecionamento().isEscolhaComercial()
                        ? new ProcessaColeta(coletaInformacoesUseCase)
                        : processoNaoFinalizadoFactory.create(mensagem);

                strategy.processar(conversa, cliente, mensagem);
            } catch (EscolhaNaoIdentificadoException ex) {
                processaOpcaoInvalida(conversa, cliente);
            }
        }

        log.info("Mensagem de uma conversa não finalizada processada com sucesso.");
    }

    @Override
    public void processarConversaFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando uma mensagem de uma conversa finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);

        LocalDateTime agora = LocalDateTime.now();

        if(conversa.getUltimaMensagemConversaFinalizada().plusSeconds(1).isBefore(agora)) {
            if (!conversa.getMensagemDirecionamento().isMensagemInicial()) {
                mensagemOrquestradora.enviarComEspera(cliente.getTelefone(), List.of(
                        mensagemBuilder.getMensagem(TipoMensagem.BOAS_VINDAS, null, null),
                        mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null, null)
                ), mensagem);
            } else {

                try {
                    ProcessoFinalizadoType strategy;

                    if (conversa.getMensagemDirecionamento().isEscolhaComercialRecontato() && !conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                        strategy = new DirecionamentoComercial(mensagemUseCase, conversaUseCase, coletaInformacoesUseCase, mensagemBuilder);
                    } else {
                        strategy = processoFinalizadoFactory.create(mensagem);
                    }

                    strategy.processar(conversa, cliente, mensagem);
                } catch (EscolhaNaoIdentificadoException ex) {
                    processaOpcaoInvalida(conversa, cliente);
                }

            }
        }

        log.info("Mensagem de uma conversa finalizada processada com sucesso.");
    }

    @Override
    public void processarConversaExistente(Cliente cliente, Mensagem mensagem) {
        log.info("Processando mensagem de uma conversa já existente. Cliente: {}, Mensagem: {}", cliente, mensagem);

        Conversa conversa = conversaUseCase.consultarPorCliente(cliente);

        if(conversa.getInativa()) {

            try {
                processamentoConversaInativaUseCase.processar(cliente, conversa, mensagem);
            } catch (EscolhaNaoIdentificadoException ex) {
                processaOpcaoInvalida(conversa, cliente);
            }

        } else if (!conversa.getFinalizada()) {
            this.processarConversaNaoFinalizada(conversa, cliente, mensagem);
        } else {
            this.processarConversaFinalizada(conversa, cliente, mensagem);
        }

        log.info("Processamento de mensagem de uma conversa já existente conclúido com sucesso.");
    }

    private void processaOpcaoInvalida(Conversa conversa, Cliente cliente) {
        log.warn("Opção inválida recebida. Reenviando mensagem anterior.");

        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.ESCOLHA_INVALIDA, null, null), cliente.getTelefone(), conversa);

        TipoMensagem ultimaMensagem = conversa.getTipoUltimaMensagem();

        if (ultimaMensagem != null) {
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(ultimaMensagem, null, null), cliente.getTelefone(), conversa);
        }
    }
}
