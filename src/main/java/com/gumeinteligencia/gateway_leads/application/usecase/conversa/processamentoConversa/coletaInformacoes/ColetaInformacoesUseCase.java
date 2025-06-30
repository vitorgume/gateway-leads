package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.coletaInformacoes;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.EstadoColeta;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColetaInformacoesUseCase {

    private final ColetaFactory coletaFactory;
    private final MensagemUseCase mensagemUseCase;
    private final MensagemBuilder mensagemBuilder;

    public void processarEtapaDeColeta(Mensagem mensagem, Cliente cliente, Conversa conversa) {
        List<EstadoColeta> mensagemColeta = conversa.getMensagemColeta();

        try {
            ColetaType coletaType = coletaFactory.create(mensagemColeta);
            coletaType.coleta(conversa, cliente, mensagem);
        } catch (EscolhaNaoIdentificadoException ex) {
            log.warn("Opção inválida recebida. Reenviando mensagem anterior.");

            mensagemUseCase.enviarMensagem(
                    mensagemBuilder.getMensagem(TipoMensagem.ESCOLHA_INVALIDA, null, null),
                    cliente.getTelefone(),
                    conversa
            );

            TipoMensagem ultimaMensagem = conversa.getTipoUltimaMensagem();

            if(ultimaMensagem != null) {
                mensagemUseCase.enviarMensagem(mensagemBuilder.getMensagem(ultimaMensagem, null, null), cliente.getTelefone(), conversa);
            }
        }
    }
}
