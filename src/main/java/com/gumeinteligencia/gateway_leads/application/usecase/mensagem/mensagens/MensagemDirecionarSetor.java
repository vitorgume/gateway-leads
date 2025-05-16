package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import org.springframework.stereotype.Component;

@Component
public class MensagemDirecionarSetor implements MensagemType{

    @Override
    public String getMensagem(String nomeVendedor) {
        return """
                Por favor, escolha a opção que melhor atende à sua necessidade:
                
                1️⃣ - Financeiro
                2️⃣ - Comercial
                0️⃣ - Encerrar atendimento
                
                💬 Por favor, informe o número correspondente à sua escolha.
                """;
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.DIRECIONAR_SETOR.getCodigo();
    }
}
