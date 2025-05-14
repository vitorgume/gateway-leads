package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GatewayMensagemUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final ChatUseCase chatUseCase;
    private final MensagemUseCase mensagemUseCase;

    public String gateway(Mensagem mensagem) {
        Optional<Cliente> clienteOptional = clienteUseCase.consultarPorTelefone(mensagem.getTelefone());


        if(clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Conversa conversa = conversaUseCase.consultarPorCliente(cliente);


            if(!conversa.getFinalizada()) {
                if(!conversa.getMensagemDirecionamento().isColetaNome()) {
                    Mensagem mensagemEnvio = Mensagem.builder()
                            .mensagem(BuilderMensagens.direcionaSetor())
                            .telefone(cliente.getTelefone())
                            .build();

                    cliente.setNome(mensagem.getMensagem());
                    conversa.getMensagemDirecionamento().setColetaNome(true);
                    clienteUseCase.salvar(cliente);
                    mensagemUseCase.enviarMensagem(mensagemEnvio);
                    conversaUseCase.salvar(conversa);
                } else {
                    if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                        chatUseCase.coletarInformacoes(mensagem, cliente, conversa);
                    } else if (mensagem.getMensagem().equals("0")) {
                        chatUseCase.encerrarAtendimento(conversa, cliente);
                    } else if (mensagem.getMensagem().equals("2")){
                        conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                        conversa = conversaUseCase.salvar(conversa);
                        chatUseCase.coletarInformacoes(mensagem, cliente, conversa);
                    } else if (mensagem.getMensagem().equals("1")) {
                        Mensagem mensagemEnvio = Mensagem.builder()
                                .mensagem(BuilderMensagens.direcinamnetoFinanceiro())
                                .telefone(cliente.getTelefone())
                                .build();

                        mensagemUseCase.enviarMensagem(mensagemEnvio);
                        mensagemUseCase.enviarContatoFinanceiro(cliente);
                        conversa.setFinalizada(true);
                        conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
                        conversaUseCase.salvar(conversa);
                    }
                }
            } else {
                if(!conversa.getMensagemDirecionamento().isMensagemInicial()) {
                    Mensagem mensagemEnvio1 = Mensagem.builder()
                            .mensagem(BuilderMensagens.boasVindas())
                            .telefone(cliente.getTelefone())
                            .build();

                    Mensagem mensagemEnvio2 = Mensagem.builder()
                            .mensagem(BuilderMensagens.direcionaSetor())
                            .telefone(cliente.getTelefone())
                            .build();

                    mensagemUseCase.enviarMensagem(mensagemEnvio1);
                    mensagemUseCase.enviarMensagem(mensagemEnvio2);
                    conversa.getMensagemDirecionamento().setMensagemInicial(true);
                    conversaUseCase.salvar(conversa);
                } else {
                    if(mensagem.getMensagem().equals("1") && !conversa.getMensagemDirecionamento().isEscolhaComercialRecontato()) {
                        if (conversa.getMensagemDirecionamento().isEscolhaFinanceiro()) {

                            Mensagem mensagemEnvio = Mensagem.builder()
                                    .mensagem(BuilderMensagens.direcionamentoOutroContatoFinanceiro())
                                    .telefone(cliente.getTelefone())
                                    .build();


                            mensagemUseCase.enviarMensagem(mensagemEnvio);
                            mensagemUseCase.enviarContatoFinanceiro(cliente);
                            conversa.getMensagemDirecionamento().setMensagemInicial(false);
                            conversaUseCase.salvar(conversa);
                        } else {
                            Mensagem mensagemEnvio = Mensagem.builder()
                                    .mensagem(BuilderMensagens.direcinamnetoFinanceiro())
                                    .telefone(cliente.getTelefone())
                                    .build();

                            mensagemUseCase.enviarMensagem(mensagemEnvio);
                            mensagemUseCase.enviarContatoFinanceiro(cliente);
                            conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
                            conversa.getMensagemDirecionamento().setMensagemInicial(false);
                            conversaUseCase.salvar(conversa);
                        }
                    } else if (mensagem.getMensagem().equals("0")) {
                        Mensagem mensagemEnvio = Mensagem.builder()
                                .mensagem(BuilderMensagens.atendimentoEncerrado())
                                .telefone(cliente.getTelefone())
                                .build();

                        mensagemUseCase.enviarMensagem(mensagemEnvio);
                        conversaUseCase.encerrar(conversa.getId());
                        clienteUseCase.inativar(cliente.getId());
                    } else {
                        if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {

                            Mensagem mensagemEnvio = Mensagem.builder()
                                    .mensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()))
                                    .telefone(cliente.getTelefone())
                                    .build();

                            mensagemUseCase.enviarMensagem(mensagemEnvio);
                            mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente, "Recontato");
                            conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                            conversa.getMensagemDirecionamento().setMensagemInicial(false);
                            conversaUseCase.salvar(conversa);
                        } else {
                            chatUseCase.coletarInformacoes(mensagem, cliente, conversa);
                            conversa.getMensagemDirecionamento().setEscolhaComercialRecontato(true);
                            conversaUseCase.salvar(conversa);
                        }
                    }
                }
            }

        } else {
            Cliente novoCliente = Cliente.builder().telefone(mensagem.getTelefone()).inativo(false).build();
            Cliente cliente = clienteUseCase.cadastrar(novoCliente);
            conversaUseCase.criar(cliente);

            Mensagem mensagemEnvio1 = Mensagem.builder()
                    .mensagem(BuilderMensagens.boasVindas())
                    .telefone(cliente.getTelefone())
                    .build();

            Mensagem mensagemEnvio2 = Mensagem.builder()
                    .mensagem(BuilderMensagens.coletaNome())
                    .telefone(cliente.getTelefone())
                    .build();

            mensagemUseCase.enviarMensagem(mensagemEnvio1);
            mensagemUseCase.enviarMensagem(mensagemEnvio2);
        }

        return "";
    }
}
