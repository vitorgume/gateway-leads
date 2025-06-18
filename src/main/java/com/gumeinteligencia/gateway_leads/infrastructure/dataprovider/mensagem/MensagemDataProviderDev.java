package com.gumeinteligencia.gateway_leads.infrastructure.dataprovider.mensagem;

import com.gumeinteligencia.gateway_leads.application.gateways.MensagemGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.ContatoRequestDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.DocumentoRequestDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.MensagemRequestDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.mensagem.Mensagem;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.ContatoMapper;
import com.gumeinteligencia.gateway_leads.infrastructure.mapper.MensagemMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class MensagemDataProviderDev implements MensagemGateway {

    @Override
    public void enviar(Mensagem mensagem) {
        MensagemRequestDto body = MensagemMapper.paraRequestDto(mensagem);
        System.out.println(body);
    }

    @Override
    public void enviarContato(String telefone, Cliente cliente) {
        ContatoRequestDto body = ContatoMapper.paraRequestDto(cliente, telefone);
        System.out.println(body);
    }

    @Override
    public void enviarRelatorio(String arquivo, String fileName, String telefone) {
        String base64ComPrefixo = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64," + arquivo;
        DocumentoRequestDto body = new DocumentoRequestDto(telefone, base64ComPrefixo, fileName);

        System.out.println(body);
    }
}
