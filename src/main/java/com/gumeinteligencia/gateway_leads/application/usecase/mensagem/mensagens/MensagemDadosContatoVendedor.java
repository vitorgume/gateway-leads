package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MensagemDadosContatoVendedor implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        StringBuilder mensagem = new StringBuilder();
        LocalDateTime dataHoje = LocalDateTime.now();

        String horaMinutos = String.format("%02d:%02d", dataHoje.getHour(), dataHoje.getMinute());

        mensagem.append("Dados do contato acima:\n");

        if(cliente.getNome() != null) {
            mensagem.append("Nome: ").append(cliente.getNome()).append("\n");
        } else {
            mensagem.append("Nome: ").append("Nome não informado").append("\n");
        }

        if(cliente.getSegmento() != null) {
            mensagem.append("Segmento: ").append(cliente.getSegmento().getDescricao()).append("\n");
        } else {
            mensagem.append("Segmento: ").append("Segmento não informado").append("\n");
        }

        mensagem.append("Hora: ").append(horaMinutos).append("\n");

        if(cliente.getRegiao() != null) {
            mensagem.append("Região: ").append(cliente.getRegiao().getDescricao());
        } else {
            mensagem.append("Região: ").append("Região não informada");
        }

        return mensagem.toString();
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DADOS_CONTATO_VENDEDOR.getCodigo();
    }
}
