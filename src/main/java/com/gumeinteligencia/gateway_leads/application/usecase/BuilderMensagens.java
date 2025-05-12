package com.gumeinteligencia.gateway_leads.application.usecase;

public class BuilderMensagens {

    public static String boasVindas() {
        return "Olá ! Muito obrigado pela sua mensagem para a Neoprint !";
    }

    public static String direcionaSetor() {
        return """
                Qual departamento deseja conversa ?
                
                1 - Financeiro
                2 - Comercial
                0 - Encerrar atendimento
                """;
    }

    public static String direcinamnetoFinanceiro() {
        return "Muito bem ! Agora você será redirecionado para a Patrícia, responsável pelo nosso financeiro. Em alguns minutos elá entrará em contato com você. Até ...";
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

    public static String coletaRegiao() {
            return """
                    Poderia me informa sua região ?
                    
                    1 - Maringá
                    2 - Região de Maringá
                    3 - Outras
                    """;
    }

    public static String direcionamentoPrimeiroContato(String nomeCliente, String nomeVendedor) {
        return "Muito obrigado pelas informações " + nomeCliente + " ! Agora você será redirecionado para o(a) "
                + nomeVendedor + ", em alguns minutos ele(a) entrará em contato com você ! Até...";
    }

    public static String direcionamentoOutroContato(String nomeVendedor) {
            return "Identifiquei que você já estava em conversa com o(a) " + nomeVendedor + ", vou repessar você novamente " +
                    "para o vendedor.";
    }

    public static String atendimentoEncerrado() {
        return "Atendimento encerrado. Até logo...";
    }
}
