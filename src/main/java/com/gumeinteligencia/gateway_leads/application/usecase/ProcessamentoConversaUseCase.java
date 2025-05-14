package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
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
public class ProcessamentoConversaUseCase {

    private final ClienteUseCase clienteUseCase;
    private final ConversaUseCase conversaUseCase;
    private final MensagemUseCase mensagemUseCase;
    private final VendedorUseCase vendedorUseCase;

    public void direcionarVendedor(Cliente cliente, Conversa conversa) {
        mensagemUseCase.enviarMensagem(BuilderMensagens.direcionamentoOutroContato(conversa.getVendedor().getNome()));
        mensagemUseCase.enviarContatoVendedor(conversa.getVendedor(), cliente, "Recontato");
    }

    public void coletarInformacoes(Mensagem mensagem, Cliente cliente, Conversa conversa) {
        MensagemColeta mensagemColeta = conversa.getMensagemColeta();

         if (!mensagemColeta.isColetaSegmento()) {
            mensagemUseCase.enviarMensagem(BuilderMensagens.coletaSegmento());
            conversa.getMensagemColeta().setColetaSegmento(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
        } else if (!mensagemColeta.isColetaMunicipio()) {
            cliente.setSegmento(GatewayEnum.gatewaySegmento(mensagem.getMensagem()));
            mensagemUseCase.enviarMensagem(BuilderMensagens.coletaRegiao());
            conversa.getMensagemColeta().setColetaMunicipio(true);
            conversa.setUltimaMensagem(LocalDateTime.now());
            conversaUseCase.salvar(conversa);
            clienteUseCase.salvar(cliente);
        } else {
            cliente.setRegiao(GatewayEnum.gatewayRegiao(mensagem.getMensagem()));
            Vendedor vendedor = vendedorUseCase.escolherVendedor(cliente);
            conversa.setVendedor(vendedor);
            conversa.setFinalizada(true);
            mensagemUseCase.enviarMensagem(
                    BuilderMensagens.direcionamentoPrimeiroContato(cliente.getNome(), vendedor.getNome()));
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

    public void conversaFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
        if(!conversa.getMensagemDirecionamento().isColetaNome()) {
            cliente.setNome(mensagem.getMensagem());
            conversa.getMensagemDirecionamento().setColetaNome(true);
            clienteUseCase.salvar(cliente);
            mensagemUseCase.enviarMensagem(BuilderMensagens.direcionaSetor());
            conversaUseCase.salvar(conversa);
        } else {
            if(conversa.getMensagemDirecionamento().isEscolhaComercial()) {
                this.coletarInformacoes(mensagem, cliente, conversa);
            } else if (mensagem.getMensagem().equals("0")) {
                mensagemUseCase.enviarMensagem(BuilderMensagens.atendimentoEncerrado());
                conversaUseCase.deletar(conversa.getId());
                clienteUseCase.deletar(cliente.getId());
            } else if (mensagem.getMensagem().equals("2")){
                conversa.getMensagemDirecionamento().setEscolhaComercial(true);
                conversa = conversaUseCase.salvar(conversa);
                this.coletarInformacoes(mensagem, cliente, conversa);
            } else if (mensagem.getMensagem().equals("1")) {
                mensagemUseCase.enviarMensagem(BuilderMensagens.direcinamnetoFinanceiro());
                mensagemUseCase.enviarContatoFinanceiro(cliente);
                conversa.setFinalizada(true);
                conversa.getMensagemDirecionamento().setEscolhaFinanceiro(true);
                conversaUseCase.salvar(conversa);
            }
        }
    }

    public void conversaNaoFinalizada(Conversa conversa, Cliente cliente, Mensagem mensagem) {
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
                    this.coletarInformacoes(mensagem, cliente, conversa);
                    conversa.getMensagemDirecionamento().setEscolhaComercialRecontato(true);
                    conversaUseCase.salvar(conversa);
                }
            }
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
