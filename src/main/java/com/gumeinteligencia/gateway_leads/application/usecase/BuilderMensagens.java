package com.gumeinteligencia.gateway_leads.application.usecase;

public class BuilderMensagens {

    public static String boasVindas() {
        return "Olá ! Muito obrigado pela sua mensagem para a Neoprint !";
    }

    public static String coletaNome() {
        return "Muito bem, antes de continuar seu atendimento, poderia me informa seu nome ?";
    }

    public static String coletaSegmento() {
        return """
                Escolha seu segmento:
                
                1 - Saúde
                2 - Celulares
                3 - Arquitetura
                4 - Engenharia
                5 - Varejo
                6 - Industria
                7 - Alimentos
                8 - Outros
                """;
    }

    public static String coletaEnderecoMunicipio() {
        return "Poderia me informa seu município ?";
    }

    public static String coletaEnderecoEstado() {
        return "E por último seu estado ?";
    }

    public static String direcionamentoPrimeiroContato(String nomeCliente, String nomeVendedor) {
        return "Muito obrigado pelas informações " + nomeCliente + " ! Agora você será redirecionado para o(a) "
                + nomeVendedor + ", em alguns minutos ele(a) entrará em contato com você ! Até...";
    }

    public static String direcionamentoOutroContato(String nomeVendedor) {
        return "Identifiquei que você já estava em conversa com o(a) " + nomeVendedor + ", vou repessar você novamente " +
                "para o vendedor.";
    }

}
