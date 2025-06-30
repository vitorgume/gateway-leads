package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoInativo;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.OutroContatoUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirecionamentoRecontatoLogistica implements ProcessamentoConversaInativa {

    private final OutroContatoUseCase outroContatoUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;
    private final ConversaUseCase conversaUseCase;

    @Override
    public void processar(Cliente cliente, Conversa conversa) {
        OutroContato outroContato = outroContatoUseCase.consultarPorNome("Gabriella");

        mensagemUseCase.enviarMensagem(
                mensagemBuilder.getMensagem(
                        TipoMensagem.DIRECIONAR_LOGISTICA, null, null
                ), cliente.getTelefone(), conversa
        );
        mensagemUseCase.enviarContatoOutroSetor(cliente, outroContato);
        conversa.getMensagemDirecionamento().add(MensagemDirecionamento.ESCOLHA_LOGISTICA);
        conversa.getMensagemDirecionamento().remove(MensagemDirecionamento.MENSAGEM_INICIAL);
        conversa.setInativa(false);
        conversaUseCase.salvar(conversa);
    }

    @Override
    public String mensagem() {
        return "3";
    }
}
