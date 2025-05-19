package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarSetor implements MensagemType{

    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return """
                Por favor, escolha a opção que melhor atende à sua necessidade:
                
                1️⃣ - Comercial
                2️⃣ - Financeiro
                0️⃣ - Encerrar atendimento
                
                💬 Por favor, informe o número correspondente à sua escolha.
                """;
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_SETOR.getCodigo();
    }
}
