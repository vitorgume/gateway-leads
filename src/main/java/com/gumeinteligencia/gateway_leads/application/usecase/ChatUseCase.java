package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final VendedorUseCase vendedorUseCase;

    public void direcionarVendedor(Cliente cliente, Conversa conversa) {
        Mensagem mensagem = Mensagem.builder()
                .mensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()))
                .telefone(cliente.getTelefone())
                .build();

        mensagemUseCase.enviarMensagem(mensagem);
        mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente, "Recontato");
    }

    public void coletarInformacoes(Mensagem mensagem, Cliente cliente, Conversa conversa) {
        MensagemColeta mensagemColeta = conversa.getMensagemColeta();
        Mensagem mensagemEnvio = new Mensagem();

        if (!mensagemColeta.isColetaSegmento()) {
            mensagemEnvio = Mensagem.builder()
                    .mensagem(BuilderMensagens.coletaSegmento())
                    .telefone(mensagem.getTelefone())
                    .build();

            mensagemUseCase.enviarMensagem(mensagemEnvio);
            conversa.getMensagemColeta().setColetaSegmento(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
        } else if (!mensagemColeta.isColetaMunicipio()) {

            if (mensagem.getMensagem().equals("0")) {
                this.encerrarAtendimento(conversa, cliente);
            } else {
                mensagemEnvio = Mensagem.builder()
                        .mensagem(BuilderMensagens.coletaRegiao())
                        .telefone(mensagem.getTelefone())
                        .build();

                cliente.setSegmento(GatewayEnum.gatewaySegmento(mensagem.getMensagem()));
                mensagemUseCase.enviarMensagem(mensagemEnvio);
                conversa.getMensagemColeta().setColetaMunicipio(true);
                conversa.setUltimaMensagem(LocalDateTime.now());
                conversaUseCase.salvar(conversa);
                clienteUseCase.salvar(cliente);
            }
        } else {

            if (mensagem.getMensagem().equals("0")) {
                this.encerrarAtendimento(conversa, cliente);
            } else {


                cliente.setRegiao(GatewayEnum.gatewayRegiao(mensagem.getMensagem()));
                Vendedor vendedor = vendedorUseCase.escolherVendedor(cliente);

                mensagemEnvio = Mensagem.builder()
                        .mensagem(BuilderMensagens.direcionamentoPrimeiroContato(vendedor.getNome()))
                        .telefone(mensagem.getTelefone())
                        .build();

                conversa.setVendedor(vendedor);
                conversa.setFinalizada(true);
                mensagemUseCase.enviarMensagem(mensagemEnvio);
                mensagemUseCase.enviarContatoVendedor(vendedor, cliente, "Contato novo");
                conversaUseCase.salvar(conversa);
                clienteUseCase.salvar(cliente);

                if(conversa.getMensagemDirecionamento().isEscolhaComercialRecontato()) {
                    conversa.getMensagemDirecionamento().setMensagemInicial(false);
                    conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                    conversaUseCase.salvar(conversa);
                }
            }
        }
    }

//    @Scheduled(cron = "0 * * * * *")
//    public void verificaAusenciaDeMensagem() {
//        List<Conversa> conversas = conversaUseCase.listarNaoFinalizados();
//
//        if(conversas.isEmpty()) {
//           log.info("Nenhuma conversa não finalizada.");
//        } else {
//            LocalDateTime agora = LocalDateTime.now();
//
//            List<Conversa> conversasAtrasadas = conversas.stream()
//                    .filter(conversa ->{
//                            if(conversa.getUltimaMensagem() != null)
//                                return conversa.getUltimaMensagem().plusMinutes(10).isBefore(agora);
//                            return false;
//                        }
//                    )
//                    .toList();
//
//
//            if(!conversasAtrasadas.isEmpty()) {
//                conversasAtrasadas.forEach(conversa -> {
//                    conversa.setFinalizada(true);
//                    conversaUseCase.salvar(conversa);
//                    Vendedor vendedor = vendedorUseCase.consultarVendedor("Mariana");
//                    mensagemUseCase
//                            .enviarContatoVendedor(
//                                    vendedor,
//                                    conversa.getCliente(),
//                                    "Contato inativo por mais de 10 minutos"
//                            );
//                });
//            }
//        }
//    }

    public void encerrarAtendimento(Conversa conversa, Cliente cliente) {
        Mensagem mensagem = Mensagem.builder()
                .mensagem(BuilderMensagens.atendimentoEncerrado())
                .telefone(cliente.getTelefone())
                .build();

        mensagemUseCase.enviarMensagem(mensagem);
        conversaUseCase.encerrar(conversa.getId());
        clienteUseCase.inativar(cliente.getId());
    }
}
