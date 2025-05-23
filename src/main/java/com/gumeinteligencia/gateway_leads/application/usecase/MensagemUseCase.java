package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa.processamentoNaoFinalizado.SetorEnvioContato;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.JanelaInicialDeBloqueio;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensagemUseCase {

    private final MensagemGateway gateway;
    private final MensagemBuilder mensagemBuilder;
    private final ConversaUseCase conversaUseCase;
    private final JanelaInicialDeBloqueio janelaInicialDeBloqueio;

    public void enviarMensagem(String textoMensagem, String telefone, Conversa conversa) {
        log.info("Enviando mensagem. Texto: {}, Telefone: {}", textoMensagem, telefone);

        if(conversa != null) {
            conversa.setDataUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
        }

        Mensagem mensagem = Mensagem.builder()
                .mensagem(textoMensagem)
                .telefone(telefone)
                .build();

        gateway.enviar(mensagem);

        log.info("Mensagem enviada com sucesso. Mensagem: {}", mensagem);
    }

    public void enviarContatoVendedor(Vendedor vendedor, Cliente cliente, String mensagem) {
        log.info("Enviando contato para vendedor. Vendedor: {}, Cliente: {}, Texto: {}", vendedor, cliente, mensagem);

        String textoMensagem = mensagemBuilder.getMensagem(TipoMensagem.DADOS_CONTATO_VENDEDOR, null, cliente);

        gateway.enviarContato(vendedor, cliente, mensagem);

        this.enviarMensagem(textoMensagem, vendedor.getTelefone(), null);
        log.info("Contato enviado com sucesso para vendedor.");
    }

    public void enviarContatoOutroSetor(Cliente cliente, SetorEnvioContato setor) {
        log.info("Enviando contato para {}. Cliente: {}", setor.getDescricao() ,cliente);
        gateway.enviarContatoOutroSetor(cliente, setor);
        log.info("Contato enviado com sucesso para {}.", setor.getDescricao());
    }

    public void enviarComEsperaDeJanela(String telefone, List<String> mensagens, Conversa conversa) {
        if (janelaInicialDeBloqueio.estaBloqueado(telefone)) {
            janelaInicialDeBloqueio.armazenarMensagens(telefone, mensagens, conversa);
            return;
        }

        janelaInicialDeBloqueio.adicionarBloqueio(telefone);
        janelaInicialDeBloqueio.armazenarMensagens(telefone, mensagens, conversa);
    }
}
