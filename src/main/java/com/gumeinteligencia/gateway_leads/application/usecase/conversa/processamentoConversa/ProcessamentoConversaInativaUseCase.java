package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.OutroContatoUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial.MensagemOrquestradora;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessamentoConversaInativaUseCase {

    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;
    private final ConversaUseCase conversaUseCase;
    private final OutroContatoUseCase outroContatoUseCase;
    private final MensagemOrquestradora mensagemOrquestradora;
    private final ClienteUseCase clienteUseCase;

    public void processar(Cliente cliente, Conversa conversa, Mensagem mensagem) {
        log.info("Processando mensagem de uma conversa inativa. Cliente: {}, Conversa: {}, Mensagem: {}", cliente, conversa, mensagem);

        if (!conversa.getMensagemDirecionamento().isMensagemInicial()) {
            mensagemOrquestradora.enviarComEspera(cliente.getTelefone(), List.of(
                    mensagemBuilder.getMensagem(TipoMensagem.BOAS_VINDAS, null, null),
                    mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null, null)
            ), mensagem);
        } else {
            switch (mensagem.getMensagem()) {
                case "1" -> {
                    mensagemUseCase.enviarMensagem(
                            mensagemBuilder.getMensagem(
                                    TipoMensagem.DIRECIONAR_OUTRO_CONTATO_COMERCIAL, conversa.getVendedor().getNome(), cliente
                            ), cliente.getTelefone(), conversa
                    );
                    mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente);
                    conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                    conversa.getMensagemDirecionamento().setMensagemInicial(false);
                    conversa.setInativa(false);
                    conversaUseCase.salvar(conversa);
                }
                case "2" -> {
                    OutroContato outroContato = outroContatoUseCase.consultarPorNome("Vitoria");

                    mensagemUseCase.enviarMensagem(
                            mensagemBuilder.getMensagem(
                                    TipoMensagem.DIRECIONAR_FINANACEIRO, null, null
                            ), cliente.getTelefone(), conversa
                    );
                    mensagemUseCase.enviarContatoOutroSetor(cliente, outroContato);
                    conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
                    conversa.getMensagemDirecionamento().setMensagemInicial(false);
                    conversa.setInativa(false);
                    conversaUseCase.salvar(conversa);
                }
                case "3" -> {
                    OutroContato outroContato = outroContatoUseCase.consultarPorNome("Gabriella");

                    mensagemUseCase.enviarMensagem(
                            mensagemBuilder.getMensagem(
                                    TipoMensagem.DIRECIONAR_LOGISTICA, null, null
                            ), cliente.getTelefone(), conversa
                    );
                    mensagemUseCase.enviarContatoOutroSetor(cliente, outroContato);
                    conversa.getMensagemDirecionamento().setEscolhaLogistica(true);
                    conversa.getMensagemDirecionamento().setMensagemInicial(false);
                    conversa.setInativa(false);
                    conversaUseCase.salvar(conversa);
                }
                case "0" -> {
                    mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.ATENDIMENTO_ENCERRADO, null, null), cliente.getTelefone(), conversa);
                    conversaUseCase.encerrar(conversa.getId());
                    clienteUseCase.inativar(cliente.getId());
                }
                default -> throw new EscolhaNaoIdentificadoException();

            }
        }

        log.info("Processamento de mensagem de uma conversa inativa concluido com sucesso.");
    }
}
