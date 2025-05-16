package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;
import org.springframework.stereotype.Component;

@Component
public class MensagemColetaSegmento implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor) {
        return """
                Por favor, qual o seu segmento de atuação?
                
                1️⃣ - Medicina e Saúde
                2️⃣ - Boutique e Lojas
                3️⃣ - Engenharia e Arquitetura
                4️⃣ - Alimentos
                5️⃣ - Celulares
                6️⃣ - Outros
                """;
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.COLETA_SEGMENTO.getCodigo();
    }
}
