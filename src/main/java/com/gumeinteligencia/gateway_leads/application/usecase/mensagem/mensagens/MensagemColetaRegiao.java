package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

import com.gumeinteligencia.gateway_leads.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class MensagemColetaRegiao implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor, Cliente cliente) {
        return """
                    Por favor, Me informa sua região ?
                    
                    1️⃣ - Maringá
                    2️⃣ - Região de Maringá
                    3️⃣ - Outras
                    0️⃣ - Encerrar
                    """;
    }

    @Override
    public Integer getTipoMensagem() {
        return TipoMensagem.COLETA_REGIAO.getCodigo();
    }
}
