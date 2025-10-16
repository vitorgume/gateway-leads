package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MensagemAvisoRecontato implements MensagemType {

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        StringBuilder mensagem = new StringBuilder();
        LocalDateTime dataHoje = LocalDateTime.now();

        String horaMinutos = String.format("%02d:%02d", dataHoje.getHour(), dataHoje.getMinute());

        mensagem.append("Cliente fez um recontato:\n");
        mensagem.append("Cliente: ").append(cliente.getNome()).append("\n");
        mensagem.append("Vendedor: ").append(nomeVendedor).append("\n");
        mensagem.append("Hora: ").append(horaMinutos);

        return mensagem.toString();
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.RECONTATO.getCodigo();
    }
}
