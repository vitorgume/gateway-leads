package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial;

import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.EsperaMensagem;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class JanelaInicialDeBloqueio {

    private final Set<String> bloqueioInicial = ConcurrentHashMap.newKeySet();
    private final Map<String, EsperaMensagem> filaMensagens = new ConcurrentHashMap<>();
    private final MensagemUseCase mensagemUseCase;

    @Value("${neoprint.delay.janela}")
    private final Long delay;

    public JanelaInicialDeBloqueio(
            MensagemUseCase mensagemUseCase,
            @Value("${neoprint.delay.janela}") Long delay
    ) {
        this.mensagemUseCase = mensagemUseCase;
        this.delay = delay;
    }

    public void adicionarSeNaoExiste(String telefone) {
        boolean adicionado = bloqueioInicial.add(telefone);

        if (adicionado) {
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                processarMensagens(telefone);
            }, delay, TimeUnit.SECONDS);
        }

    }

    public void armazenarMensagens(String telefone, List<String> mensagens, Mensagem ultima) {
        EsperaMensagem espera = new EsperaMensagem();
        espera.setMensagensParaEnviar(mensagens);
        espera.setUltimaMensagemRecebida(ultima);
        filaMensagens.put(telefone, espera);

    }

    private void processarMensagens(String telefone) {
        removerBloqueio(telefone);
        EsperaMensagem espera = filaMensagens.remove(telefone);

        if (espera != null) {
            mensagemUseCase.executarEnvio(
                    telefone,
                    espera.getMensagensParaEnviar(),
                    espera.getUltimaMensagemRecebida()
            );
        }
    }

    public void removerBloqueio(String telefone) {
        bloqueioInicial.remove(telefone);
    }
}
