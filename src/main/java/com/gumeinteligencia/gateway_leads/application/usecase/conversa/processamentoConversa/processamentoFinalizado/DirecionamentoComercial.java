package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemDirecionamento;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.EscolhaMensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DirecionamentoComercial implements ProcessoFinalizadoType{

    private final MensagemUseCase mensagemUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ColetaInformacoesUseCase coletaInformacoesUseCase;
    private final MensagemBuilder mensagemBuilder;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando escolha comercial de uma conversa finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
        if(conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.ESCOLHA_COMERCIAL) || conversa.getVendedor() != null) {
            mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente);
            conversa.getMensagemDirecionamento().add(MensagemDirecionamento.ESCOLHA_COMERCIAL);
            conversa.getMensagemDirecionamento().remove(MensagemDirecionamento.MENSAGEM_INICIAL);
            conversaUseCase.salvar(conversa);
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_OUTRO_CONTATO_COMERCIAL, conversa.getVendedor().getNome(), null), cliente.getTelefone(), conversa);
        } else {
            coletaInformacoesUseCase.processarEtapaDeColeta(mensagem, cliente, conversa);
            conversa.getMensagemDirecionamento().add(MensagemDirecionamento.ESCOLHA_COMERCIAL_RECONTATO);
            conversaUseCase.salvar(conversa);
        }
        log.info("Processamento de escolha comercial de uma conversa finalizada concluído com sucesso. Conversa: {}", conversa);
    }

    @Override
    public Integer getTipoMensagem() {
        return EscolhaMensagem.ESCOLHA_COMERCIAL.getCodigo();
    }
}
