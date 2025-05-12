package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Endereco;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final VendedorUseCase vendedorUseCase;

    public void direcionarVendedor(Cliente cliente, Conversa conversa) {
        mensagemUseCase.enviarMensagem(BuilderMensagens.boasVindas());
        mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()));
        mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente, "Recontato");
    }

    public void coletarInformacoes(Mensagem mensagem, Cliente cliente, Conversa conversa) {
        MensagemColeta mensagemColeta = conversa.getMensagemColeta();

        if(!mensagemColeta.isColetaNome()) {
            mensagemUseCase.enviarMensagem(BuilderMensagens.boasVindas());
            mensagemUseCase.enviarMensagem(BuilderMensagens.coletaNome());
            conversa.getMensagemColeta().setColetaNome(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
        } else if (!mensagemColeta.isColetaSegmento()) {
            cliente.setNome(mensagem.getMensagem());
            mensagemUseCase.enviarMensagem(BuilderMensagens.coletaSegmento());
            conversa.getMensagemColeta().setColetaSegmento(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);
        } else if (!mensagemColeta.isColetaMunicipio()) {
            cliente.setSegmento(GatewaySegmento.gateway(mensagem.getMensagem()));
            mensagemUseCase.enviarMensagem(BuilderMensagens.coletaEnderecoMunicipio());
            conversa.getMensagemColeta().setColetaMunicipio(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);
        } else if (!mensagemColeta.isColetaEstado()) {
            cliente.setEndereco(Endereco.builder().municipio(mensagem.getMensagem().toLowerCase()).build());
            mensagemUseCase.enviarMensagem(BuilderMensagens.coletaEnderecoEstado());
            conversa.getMensagemColeta().setColetaEstado(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);
        } else {
            cliente.getEndereco().setEstado(mensagem.getMensagem());
            Vendedor vendedor = vendedorUseCase.escolherVendedor(cliente);
            conversa.setVendedor(vendedor);
            conversa.setFinalizada(true);
            mensagemUseCase.enviarMensagem(
                    BuilderMensagens.direcionamentoPrimeiroContato(cliente.getNome(), vendedor.getNome()));
            mensagemUseCase.enviarContatoVendedor(vendedor, cliente, "Contato novo");
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void verificaAusenciaDeMensagem() {
        List<Conversa> conversas = conversaUseCase.listarNaoFinalizados();

        LocalDateTime agora = LocalDateTime.now();

        List<Conversa> conversasAtrasadas = conversas.stream()
                .filter(conversa ->
                        conversa.getUltimaMensagem().plusMinutes(10).isBefore(agora)
                )
                .toList();


        if(!conversasAtrasadas.isEmpty()) {
            conversasAtrasadas.forEach(conversa -> {
                conversa.setFinalizada(true);
                conversaUseCase.salvar(conversa);
                Vendedor vendedor = vendedorUseCase.consultarVendedor("Mariana");
                mensagemUseCase
                        .enviarContatoVendedor(
                                vendedor,
                                conversa.getCliente(),
                                "Contato inativo por mais de 10 minutos"
                        );
            });
        }
    }
}
