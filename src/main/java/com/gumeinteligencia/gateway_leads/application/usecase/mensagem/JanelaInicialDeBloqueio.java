package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JanelaInicialDeBloqueio {
    private final Set<String> bloqueioInicial = ConcurrentHashMap.newKeySet();
    private final Map<String, EsperaMensagem> filaMensagens = new ConcurrentHashMap<>();
    private final MensagemUseCase mensagemUseCase;

    public boolean adicionarSeNaoExiste(String telefone) {
        boolean adicionado = bloqueioInicial.add(telefone);

        if (adicionado) {
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                processarMensagens(telefone);
            }, 25, TimeUnit.SECONDS);
        }

        return adicionado;
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
