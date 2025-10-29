package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoInativo;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.CrmUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.OutroContatoUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirecionarRecontatoComercial implements ProcessamentoConversaInativa {

    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;
    private final ConversaUseCase conversaUseCase;
    private final OutroContatoUseCase outroContatoUseCase;

    @Override
    public void processar(Cliente cliente, Conversa conversa) {
        mensagemUseCase.enviarMensagem(
                mensagemBuilder.getMensagem(
                        TipoMensagem.DIRECIONAR_OUTRO_CONTATO_COMERCIAL, conversa.getVendedor().getNome(), cliente
                ), cliente.getTelefone(), conversa
        );

        mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente);


        mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.RECONTATO, conversa.getVendedor().getNome(), cliente), outroContatoUseCase.consultarPorNome("Ana").getTelefone(), null);
        conversa.getMensagemDirecionamento().add(MensagemDirecionamento.ESCOLHA_COMERCIAL);
        conversa.getMensagemDirecionamento().remove(MensagemDirecionamento.MENSAGEM_INICIAL);
        conversaUseCase.salvar(conversa);
    }

    @Override
    public String mensagem() {
        return "1";
    }
}
