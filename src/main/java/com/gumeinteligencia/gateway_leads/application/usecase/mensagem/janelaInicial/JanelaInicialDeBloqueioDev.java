package com.gumeinteligencia.gateway_leads.application.usecase.mensagem.janelaInicial;

import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.EsperaMensagem;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class JanelaInicialDeBloqueioDev implements JanelaInicial {
    private final Set<String> bloqueioInicial = ConcurrentHashMap.newKeySet();
    private final Map<String, EsperaMensagem> filaMensagens = new ConcurrentHashMap<>();
    private final MensagemUseCase mensagemUseCase;

    @Override
    public void adicionarSeNaoExiste(String telefone) {
        boolean adicionado = bloqueioInicial.add(telefone);

        if (adicionado) {
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                processarMensagens(telefone);
            }, 2, TimeUnit.SECONDS);
        }

    }

    @Override
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

    @Override
    public void removerBloqueio(String telefone) {
        bloqueioInicial.remove(telefone);
    }
}
