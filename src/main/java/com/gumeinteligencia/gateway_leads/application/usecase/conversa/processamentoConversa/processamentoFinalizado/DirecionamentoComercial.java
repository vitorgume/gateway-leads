package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoFinalizado;

import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.CrmUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.OutroContatoUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes.ColetaInformacoesUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
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
    private final OutroContatoUseCase outroContatoUseCase;

    @Override
    public void processar(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        log.info("Processando escolha comercial de uma conversa finalizada. Conversa: {}, Cliente: {}, Mensagem: {}", conversa, cliente, mensagem);
        if(conversa.getMensagemDirecionamento().contains(MensagemDirecionamento.ESCOLHA_COMERCIAL) || conversa.getVendedor() != null) {
            conversa.getMensagemDirecionamento().add(MensagemDirecionamento.ESCOLHA_COMERCIAL);
            conversa.getMensagemDirecionamento().remove(MensagemDirecionamento.MENSAGEM_INICIAL);
            conversaUseCase.salvar(conversa);
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.DIRECIONAR_OUTRO_CONTATO_COMERCIAL, conversa.getVendedor().getNome(), null), cliente.getTelefone(), conversa);
            mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.RECONTATO, conversa.getVendedor().getNome(), cliente), outroContatoUseCase.consultarPorNome("Ana").getTelefone(), null);
        } else {
            if(!conversa.getMensagemColeta().contains(EstadoColeta.COLETA_REGIAO) && !conversa.getMensagemColeta().contains(EstadoColeta.FINALIZA_COLETA)) {
                conversa.getMensagemColeta().add(EstadoColeta.COLETA_SEGMENTO);
            }

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
