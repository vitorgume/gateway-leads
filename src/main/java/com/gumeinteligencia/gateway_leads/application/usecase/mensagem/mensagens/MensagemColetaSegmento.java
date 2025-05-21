package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.TipoMensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemColetaSegmento implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return """
                Por favor, qual o seu segmento de atuação?
                
                1️⃣ - Medicina e Saúde
                2️⃣ - Boutique e Lojas
                3️⃣ - Engenharia e Arquitetura
                4️⃣ - Alimentos
                5️⃣ - Celulares
                6️⃣ - Outros
                0️⃣ - Encerrar
                """;
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.COLETA_SEGMENTO.getCodigo();
    }
}
