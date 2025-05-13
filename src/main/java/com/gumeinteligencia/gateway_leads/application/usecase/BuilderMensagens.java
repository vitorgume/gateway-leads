package com.gumeinteligencia.gateway_leads.application.usecase;

public class BuilderMensagens {

    public static String boasVindas() {
        return "Olá! Muito obrigado pelo interesse em conversar com a Neoprint, será um prazer ajudá-la(o)!";
    }

    public static String direcionaSetor() {
        return """
                Por favor, escolha a opção que melhor atende à sua necessidade:
                
                1️⃣ - Financeiro
                2️⃣ - Comercial
                0️⃣ - Encerrar atendimento
                
                💬 Por favor, informe o número correspondente à sua escolha.
                """;
    }

    public static String direcinamnetoFinanceiro() {
        return "Muito bem ! Agora você será redirecionado para a Patrícia, responsável pelo nosso financeiro. Em alguns minutos elá entrará em contato com você. Até ...";
    }

    public static String coletaNome() {
        return "Antes de continuar seu atendimento, me informa seu nome, por favor ? ";
    }

    public static String coletaSegmento() {
        return """
                Por favor, qual o seu segmento de atuação?
                
                1️⃣ - Medicina e Saúde
                2️⃣ - Boutique e Lojas
                3️⃣ - Engenharia e Arquitetura
                4️⃣ - Alimentos
                5️⃣ - Celulares
                6️⃣ - Outros
                0️⃣ - Encerrar atendimento
                """;
    }

    public static String coletaRegiao() {
            return """
                    Por favor, Me informa sua região ?
                    
                    1️⃣ - Maringá
                    2️⃣ - Região de Maringá
                    3️⃣ - Outras
                    0️⃣ - Encerrar atendimento
                    """;
    }

    public static String direcionamentoPrimeiroContato(String nomeVendedor) {
        return "Muito obrigado pelas informações ! Agora você será redirecionado para o(a) "
                + nomeVendedor + ", em alguns minutos ele(a) entrará em contato com você ! Até...";
    }

    public static String direcionamentoOutroContato(String nomeVendedor) {
            return "Identifiquei que você já estava em conversa com o(a) " + nomeVendedor + ", vou repessar você novamente " +
                    "para o vendedor.";
    }

    public static String atendimentoEncerrado() {
        return "Atendimento encerrado. Até logo...";
    }

    public static String direcionamentoOutroContatoFinanceiro() {
        return "Identifiquei que você já estava em conversa com a Patrícia, vou repassar você novamente para ela.";
    }
}
