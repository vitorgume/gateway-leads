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

    private final ClienteUseCase clienteuseCase;
    private final ConversaUseCase conversaUseCase;
    private final ChatUseCase chatUseCase;
    private final MensagemUseCase mensagemUseCase;

    public String gateway(Mensagem mensagem) {
        Optional<Cliente> clienteOptional = clienteuseCase.consultarPorTelefone(mensagem.getTelefone());


        if(clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Conversa conversa = conversaUseCase.consultarPorCliente(cliente);


            if(!conversa.getFinalizada()) {
                if(!conversa.getMensagemDirecionamento().isColetaNome()) {
                    cliente.setNome(mensagem.getMensagem());
                    conversa.getMensagemDirecionamento().setColetaNome(true);
                    clienteuseCase.salvar(cliente);
                    mensagemUseCase.enviarMensagem(BuilderMensagens.direcionaSetor());
                    conversaUseCase.salvar(conversa);
                } else {
                    if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                        chatUseCase.coletarInformacoes(mensagem, cliente, conversa);
                    } else if (mensagem.getMensagem().equals("0")) {
                        mensagemUseCase.enviarMensagem(BuilderMensagens.atendimentoEncerrado());
                        conversaUseCase.deletar(conversa.getId());
                        clienteuseCase.deletar(cliente.getId());
                    } else if (mensagem.getMensagem().equals("2")){
                        conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                        conversa = conversaUseCase.salvar(conversa);
                        chatUseCase.coletarInformacoes(mensagem, cliente, conversa);
                    } else if (mensagem.getMensagem().equals("1")) {
                        mensagemUseCase.enviarMensagem(BuilderMensagens.direcinamnetoFinanceiro());
                        mensagemUseCase.enviarContatoFinanceiro(cliente);
                        conversa.setFinalizada(true);
                        conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
                        conversaUseCase.salvar(conversa);
                    }
                }
            } else {
                if(!conversa.getMensagemDirecionamento().isMensagemInicial()) {
                    mensagemUseCase.enviarMensagem(BuilderMensagens.boasVindas());
                    mensagemUseCase.enviarMensagem(BuilderMensagens.direcionaSetor());
                    conversa.getMensagemDirecionamento().setMensagemInicial(true);
                    conversaUseCase.salvar(conversa);
                } else {
                    if(mensagem.getMensagem().equals("1") && !conversa.getMensagemDirecionamento().isEscolhaComercialRecontato()) {
                        if (conversa.getMensagemDirecionamento().isEscolhaFinanceiro()) {
                            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContatoFinanceiro());
                            mensagemUseCase.enviarContatoFinanceiro(cliente);
                            conversa.getMensagemDirecionamento().setMensagemInicial(false);
                            conversaUseCase.salvar(conversa);
                        } else {
                            mensagemUseCase.enviarMensagem(BuilderMensagens.direcinamnetoFinanceiro());
                            mensagemUseCase.enviarContatoFinanceiro(cliente);
                            conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
                            conversa.getMensagemDirecionamento().setMensagemInicial(false);
                            conversaUseCase.salvar(conversa);
                        }
                    } else if (mensagem.getMensagem().equals("0")) {
                        mensagemUseCase.enviarMensagem(BuilderMensagens.atendimentoEncerrado());
                        conversaUseCase.deletar(conversa.getId());
                        clienteuseCase.deletar(cliente.getId());
                    } else {
                        if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()));
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
            Cliente novoCliente = Cliente.builder().telefone(mensagem.getTelefone()).build();
            Cliente cliente = clienteuseCase.cadastrar(novoCliente);
            Conversa conversa = conversaUseCase.criar(cliente);
            mensagemUseCase.enviarMensagem(BuilderMensagens.boasVindas());
            mensagemUseCase.enviarMensagem(BuilderMensagens.coletaNome());
        }

        return "";
    }
}
