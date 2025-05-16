package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.mensagens;

public class MensagemColetaRegiao implements MensagemType{
    @Override
    public String getMensagem(String nomeVendedor) {
        return """
                    Por favor, Me informa sua região ?
                    
                    1 - Maringá
                    2 - Região de Maringá
                    3 - Outras
                    """;
    }

    @Override
    public int getTipoMensagem() {
        return TipoMensagem.COLETA_REGIAO.getCodigo();
    }
}
