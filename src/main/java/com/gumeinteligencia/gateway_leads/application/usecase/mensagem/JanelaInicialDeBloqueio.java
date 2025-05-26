package com.gumeinteligencia.gateway_leads.application.usecase.mensagem;

import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
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
    private final ProcessarMensagemUseCase processarMensagemUseCase;
    private final MensagemUseCase mensagemUseCase;

    public boolean estaBloqueado(String telefone) {
        return bloqueioInicial.contains(telefone);
    }

    public void adicionarBloqueio(String telefone) {
        bloqueioInicial.add(telefone);

        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            processarMensagens(telefone);
        }, 25, TimeUnit.SECONDS);
    }

    public void armazenarMensagens(String telefone, List<String> mensagens, Conversa conversa) {
        EsperaMensagem espera = new EsperaMensagem();
        espera.setMensagensParaEnviar(mensagens);
        espera.setConversa(conversa);
        filaMensagens.put(telefone, espera);
    }

    private void processarMensagens(String telefone) {
        removerBloqueio(telefone);
        EsperaMensagem espera = filaMensagens.remove(telefone);

        if (espera != null) {
            List<String> mensagens = espera.getMensagensParaEnviar();
            Conversa conversa = espera.getConversa();

            if (mensagens != null && !mensagens.isEmpty()) {
                for (String mensagem : mensagens) {
                    mensagemUseCase.enviarMensagem(mensagem, telefone, conversa);
                }
            }

            if (espera.getUltimaMensagemRecebida() != null) {
                processarMensagemUseCase.processarNovaMensagem(espera.getUltimaMensagemRecebida());
            }
        }
    }

    public void removerBloqueio(String telefone) {
        bloqueioInicial.remove(telefone);
    }

}
