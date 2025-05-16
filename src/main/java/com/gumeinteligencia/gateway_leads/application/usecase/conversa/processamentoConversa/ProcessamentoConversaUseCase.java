package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.ProcessoFinalizadoFactory;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.ProcessoFinalizadoType;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessaColeta;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessoNaoFinalizadoFactory;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.ProcessoNaoFinalizadoType;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessamentoConversaUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;
    private final ProcessoNaoFinalizadoFactory processoNaoFinalizadoFactory;
    private final ProcessoFinalizadoFactory processoFinalizadoFactory;
    private final MensagemBuilder mensagemBuilder;

    public void processarConversaNaoFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if(!conversa.getMensagemDirecionamento().isColetaNome()) {
            cliente.setNome(mensagem.getMensagem());
            conversa.getMensagemDirecionamento().setColetaNome(true);
            clienteUseCase.salvar(cliente);
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null));
            conversaUseCase.salvar(conversa);
        } else {

            ProcessoNaoFinalizadoType strategy = conversa.getMensagemDirecionamento().isEscolhaComercial()
                    ? new ProcessaColeta(coletaInformacoesUseCase)
                    : processoNaoFinalizadoFactory.create(mensagem);

            strategy.processar(conversa, cliente, mensagem);

        }
    }

    public void processarConversaFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if(!conversa.getMensagemDirecionamento().isMensagemInicial()) {
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.BOAS_VINDAS, null));
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_SETOR, null));
            conversa.getMensagemDirecionamento().setMensagemInicial(true);
            conversaUseCase.salvar(conversa);
        } else {
            ProcessoFinalizadoType strategy = processoFinalizadoFactory.create(mensagem);

            strategy.processar(conversa, cliente, mensagem);
        }
    }
}
