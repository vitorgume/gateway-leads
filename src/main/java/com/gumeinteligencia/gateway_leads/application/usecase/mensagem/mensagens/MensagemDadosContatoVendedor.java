package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MensagemDadosContatoVendedor implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        StringBuilder mensagem = new StringBuilder();
        LocalDateTime dataHoje = LocalDateTime.now();

        String horaMinutos = dataHoje.getHour() + ":" + dataHoje.getMinute();

        mensagem.append("Dados do contato acima:\n");
        mensagem.append("Nome: ").append(cliente.getNome()).append("\n");
        mensagem.append("Segmento: ").append(cliente.getSegmento().getDescricao()).append("\n");
        mensagem.append("Hora: ").append(horaMinutos).append("\n");
        mensagem.append("Região: ").append(cliente.getRegiao().getDescricao());

        return mensagem.toString();
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DADOS_CONTATO_VENDEDOR.getCodigo();
    }
}
