package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.ConversaUseCase;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens.MensagemBuilder;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.outroContato.OutroContato;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensagemUseCase {

    private final MensagemGateway gateway;
    private final MensagemBuilder mensagemBuilder;
    private final ConversaUseCase conversaUseCase;
    private final ClienteUseCase clienteUseCase;

    public void enviarMensagem(String textoMensagem, String telefone, Conversa conversa) {
        log.info("Enviando mensagem. Texto: {}, Telefone: {}", textoMensagem, telefone);

        if(conversa != null) {
            conversa.setUltimaMensagem(LocalDateTime.now());

            if(conversa.getFinalizada() && conversa.getUltimaMensagemConversaFinalizada() == null) {
                conversa.setUltimaMensagemConversaFinalizada(conversa.getUltimaMensagem());
            }

            conversaUseCase.salvar(conversa);
        }

        Mensagem mensagem = Mensagem.builder()
                .mensagem(textoMensagem)
                .telefone(telefone)
                .build();

        gateway.enviar(mensagem);

        log.info("Mensagem enviada com sucesso. Mensagem: {}", mensagem);
    }

    public void enviarMensagemVendedor(String mensagem, String telefone, Conversa conversa) {
        this.enviarMensagem(mensagem, telefone, conversa);
        this.enviarMensagem(mensagemBuilder.getMensagem(TipoMensagem.SEPARACAO_CONTATOS, null, null), telefone, conversa);
    }

    public void enviarContatoVendedor(Vendedor vendedor, Cliente cliente, String mensagem) {
        log.info("Enviando contato para vendedor. Vendedor: {}, Cliente: {}, Texto: {}", vendedor, cliente, mensagem);

        String textoMensagem = mensagemBuilder.getMensagem(TipoMensagem.DADOS_CONTATO_VENDEDOR, null, cliente);

        gateway.enviarContato(vendedor, cliente, mensagem);

        this.enviarMensagemVendedor(textoMensagem, vendedor.getTelefone(), null);
        log.info("Contato enviado com sucesso para vendedor.");
    }

    public void enviarContatoOutroSetor(Cliente cliente, OutroContato outroContato) {
        log.info("Enviando contato para {}. Cliente: {}", outroContato.getSetor().getDescricao() ,cliente);
        gateway.enviarContatoOutroSetor(cliente, outroContato.getTelefone());
        log.info("Contato enviado com sucesso para {}.", outroContato.getSetor().getDescricao());
    }

    public void enviarRelatorio(String arquivo, String fileName, String telefone) {
        log.info("Enviando relatório de vendedores.");
        gateway.enviarRelatorio(arquivo, fileName, telefone);
        log.info("Relatório enviado com sucesso.");
    }

    public void executarEnvio(String telefone, List<String> mensagens, Mensagem mensagemRecebida) {
        Optional<Cliente> clienteExistente = clienteUseCase.consultarPorTelefone(telefone);
        Conversa conversa;

        if(clienteExistente.isEmpty()) {
            Cliente novoCliente = Cliente.builder().telefone(mensagemRecebida.getTelefone()).build();
            Cliente cliente = clienteUseCase.cadastrar(novoCliente);
            conversa = conversaUseCase.criar(cliente);
        } else {
            Conversa conversaExistente = conversaUseCase.consultarPorCliente(clienteExistente.get());

            conversaExistente.getMensagemDirecionamento().setMensagemInicial(true);
            conversaExistente.setTipoUltimaMensagem(TipoMensagem.DIRECIONAR_SETOR);
            conversa = conversaUseCase.salvar(conversaExistente);
        }

        for (String mensagem : mensagens) {
            this.enviarMensagem(mensagem, telefone, conversa);
        }
    }
}
