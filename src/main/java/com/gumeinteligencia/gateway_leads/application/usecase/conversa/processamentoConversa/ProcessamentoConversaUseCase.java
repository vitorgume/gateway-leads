package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado.*;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.*;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessamentoConversaUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;
    private final ProcessamentoNaoFinalizadoUseCase processamentoNaoFinalizadoUseCase;
    private final ProcessamentoFinalizadoUseCase processamentoFinalizadoUseCase;

    public void processarConversaNaoFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if(!conversa.getMensagemDirecionamento().isColetaNome()) {
            cliente.setNome(mensagem.getMensagem());
            conversa.getMensagemDirecionamento().setColetaNome(true);
            clienteUseCase.salvar(cliente);
            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionaSetor());
            conversaUseCase.salvar(conversa);
        } else {
            ProcessoNaoFinalizadoType processoNaoFinalizadoType = null;

            if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                coletaInformacoesUseCase.processarEtapaDeColeta(mensagem, cliente, conversa);
            } else if (mensagem.getMensagem().equals("0")) {
                processoNaoFinalizadoType = new ProcessaEncerramento(mensagemUseCase, conversaUseCase, clienteUseCase);
            } else if (mensagem.getMensagem().equals("2")){
                processoNaoFinalizadoType = new ProcessaEscolhaComercial(conversaUseCase, coletaInformacoesUseCase);
            } else if (mensagem.getMensagem().equals("1")) {
                processoNaoFinalizadoType = new ProcessaEscolhaFinanceiro(mensagemUseCase, conversaUseCase);
            }

            ProcessoNaoFinalizadoType processoNaoFinalizadoTypeSetado = processamentoNaoFinalizadoUseCase.setProcessoFinalizadoType(processoNaoFinalizadoType);
            processoNaoFinalizadoTypeSetado.processar(conversa, cliente, mensagem);
        }
    }

    public void processarConversaFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if(!conversa.getMensagemDirecionamento().isMensagemInicial()) {
            mensagemUseCase.enviarMensagem(BuilderMensagens.boasVindas());
            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionaSetor());
            conversa.getMensagemDirecionamento().setMensagemInicial(true);
            conversaUseCase.salvar(conversa);
        } else {
            ProcessoFinalizadoType processoFinalizoType;

            if(mensagem.getMensagem().equals("1") && !conversa.getMensagemDirecionamento().isEscolhaComercialRecontato()) {
                processoFinalizoType = new DirecionamentoFinanceiro(mensagemUseCase, conversaUseCase);
            } else if (mensagem.getMensagem().equals("0")) {
                processoFinalizoType = new DirecionamentoEncerramento(mensagemUseCase, conversaUseCase, clienteUseCase);
            } else {
               processoFinalizoType = new DirecionamentoComercial(mensagemUseCase, conversaUseCase, coletaInformacoesUseCase);
            }

            ProcessoFinalizadoType processoFinalizadoTypeSetado = processamentoFinalizadoUseCase.setProcessoFinalizadoType(processoFinalizoType);
            processoFinalizadoTypeSetado.processar(conversa, cliente, mensagem);
        }
    }
}
