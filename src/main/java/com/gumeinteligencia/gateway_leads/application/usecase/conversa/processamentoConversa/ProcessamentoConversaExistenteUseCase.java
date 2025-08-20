package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoInativo.ProcessamentoConversaInativaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial.MensagemOrquestradora;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.DirecionamentoComercial;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.ProcessoFinalizadoFactory;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.ProcessoFinalizadoType;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessaColeta;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessoNaoFinalizadoFactory;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessoNaoFinalizadoType;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProcessamentoConversaExistenteUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;
    private final ProcessoNaoFinalizadoFactory processoNaoFinalizadoFactory;
    private final ProcessoFinalizadoFactory processoFinalizadoFactory;
    private final MensagemBuilder mensagemBuilder;
    private final MensagemOrquestradora mensagemOrquestradora;
    private final ProcessamentoConversaInativaUseCase processamentoConversaInativaUseCase;

    @Value("${spring.profiles.active}")
    private final String profile;

    public ProcessamentoConversaExistenteUseCase(
            ClienteUseCase clienteUseCase,
            ConversaUseCase conversaUseCase,
            MensagemUseCase mensagemUseCase,
            ColetaInformacoesUseCase coletaInformacoesUseCase,
            ProcessoNaoFinalizadoFactory processoNaoFinalizadoFactory,
            ProcessoFinalizadoFactory processoFinalizadoFactory,
            MensagemBuilder mensagemBuilder,
            MensagemOrquestradora mensagemOrquestradora,
            ProcessamentoConversaInativaUseCase processamentoConversaInativaUseCase,
            @Value("${spring.profiles.active}") String profile) {
        this.clienteUseCase = clienteUseCase;
        this.conversaUseCase = conversaUseCase;
        this.mensagemUseCase = mensagemUseCase;
        this.coletaInformacoesUseCase = coletaInformacoesUseCase;
        this.processoNaoFinalizadoFactory = processoNaoFinalizadoFactory;
        this.processoFinalizadoFactory = processoFinalizadoFactory;
        this.mensagemBuilder = mensagemBuilder;
        this.mensagemOrquestradora = mensagemOrquestradora;
        this.processamentoConversaInativaUseCase = processamentoConversaInativaUseCase;
        this.profile = profile;
    }

    public void processarConversaNaoFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando mensagem de uma conversa não finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
        if (!conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.COLETA_NOME)) {
            cliente.setNome(mensagem.getMensagem());
            conversa.getMensagemDirecionamento().add(MensagemDirecionamento.COLETA_NOME);
            clienteUseCase.salvar(cliente);
            conversa.setTipoUltimaMensagem(TipoMensagem.DIRECIONAR_SETOR);
            conversaUseCase.salvar(conversa);
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null, null), cliente.getTelefone(), conversa);
        } else {

            try {
                ProcessoNaoFinalizadoType strategy = conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.ESCOLHA_COMERCIAL)
                        ? new ProcessaColeta(coletaInformacoesUseCase)
                        : processoNaoFinalizadoFactory.create(mensagem);

                strategy.processar(conversa, cliente, mensagem);
            } catch (EscolhaNaoIdentificadoException ex) {
                processaOpcaoInvalida(conversa, cliente);
            }
        }

        log.info("Mensagem de uma conversa não finalizada processada com sucesso.");
    }

    public void processarConversaFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando uma mensagem de uma conversa finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);

        LocalDateTime agora = LocalDateTime.now();

        boolean pausaUltimaMensagem = profile.equals("prod")
                ? conversa.getUltimaMensagemConversaFinalizada().plusHours(1).isBefore(agora)
                : conversa.getUltimaMensagemConversaFinalizada().plusSeconds(1).isBefore(agora);


        if(pausaUltimaMensagem) {
            System.out.println("oi");
            if (!conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.MENSAGEM_INICIAL)) {
                System.out.println("oi");
                mensagemOrquestradora.enviarComEspera(cliente.getTelefone(), List.of(
                        mensagemBuilder.getMensagem(TipoMensagem.BOAS_VINDAS, null, null),
                        mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null, null)
                ), mensagem);
            } else {
                System.out.println("oi");
                try {
                    ProcessoFinalizadoType strategy;

                    if (conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.ESCOLHA_COMERCIAL_RECONTATO) && !conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.ESCOLHA_COMERCIAL)) {
                        System.out.println("oi");
                        strategy = new DirecionamentoComercial(mensagemUseCase, conversaUseCase, coletaInformacoesUseCase, mensagemBuilder);
                    } else {
                        System.out.println("oi");
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
