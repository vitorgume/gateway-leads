package com.gumeinteligencia.gateway_leads.application.usecase.conversa.processamentoConversa;

import com.gumeinteligencia.gateway_leads.application.usecase.*;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import com.gumeinteligencia.gateway_leads.domain.conversa.Mensagem;
import com.gumeinteligencia.gateway_leads.domain.conversa.MensagemColeta;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProcessamentoConversaUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final VendedorUseCase vendedorUseCase;

    public void direcionarVendedor(Cliente cliente, Conversa conversa) {
        mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()));
        mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente, "Recontato");
    }



    public void processarConversaFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if(!conversa.getMensagemDirecionamento().isColetaNome()) {
            cliente.setNome(mensagem.getMensagem());
            conversa.getMensagemDirecionamento().setColetaNome(true);
            clienteUseCase.salvar(cliente);
            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionaSetor());
            conversaUseCase.salvar(conversa);
        } else {
            if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                this.processarEtapaDeColeta(mensagem, cliente, conversa);
            } else if (mensagem.getMensagem().equals("0")) {
                mensagemUseCase.enviarMensagem(BuilderMensagens.atendimentoEncerrado());
                conversaUseCase.deletar(conversa.getId());
                clienteUseCase.deletar(cliente.getId());
            } else if (mensagem.getMensagem().equals("2")){
                conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                conversa = conversaUseCase.salvar(conversa);
                this.processarEtapaDeColeta(mensagem, cliente, conversa);
            } else if (mensagem.getMensagem().equals("1")) {
                mensagemUseCase.enviarMensagem(BuilderMensagens.direcinamnetoFinanceiro());
                mensagemUseCase.enviarContatoFinanceiro(cliente);
                conversa.setFinalizada(true);
                conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
                conversaUseCase.salvar(conversa);
            }
        }
    }

    public void processarConversaNaoFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
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
                clienteUseCase.deletar(cliente.getId());
            } else {
                if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                    mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()));
                    mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente, "Recontato");
                    conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                    conversa.getMensagemDirecionamento().setMensagemInicial(false);
                    conversaUseCase.salvar(conversa);
                } else {
                    this.processarEtapaDeColeta(mensagem, cliente, conversa);
                    conversa.getMensagemDirecionamento().setEscolhaComercialRecontato(true);
                    conversaUseCase.salvar(conversa);
                }
            }
        }
    }
}
