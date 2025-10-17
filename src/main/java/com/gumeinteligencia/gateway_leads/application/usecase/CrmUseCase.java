package com.gumeinteligencia.gateway_leads.application.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumeinteligencia.gateway_leads.application.exceptions.LeadNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.CrmGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.CardDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.CustomFieldDto;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.CustomFieldValueDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import com.gumeinteligencia.gateway_leads.domain.conversa.Conversa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrmUseCase {

    private final CrmGateway gateway;

    public void atualizarCrm(Vendedor vendedor, Cliente cliente, Conversa conversa) {
        log.info("Atualizando crm. Vendedor: {}, Cliente: {}, Conversa: {}", vendedor, cliente, conversa);

        Integer idLead = this.consultaLeadPeloTelefone(cliente.getTelefone());

        log.info("Construindo body para atualizar card.");

        List<CustomFieldDto> customFieldDtos = new ArrayList<>();

        customFieldDtos.add(selectField(1486843, cliente.getSegmento() == null ? 1242461 : cliente.getSegmento().getIdCrm()));

        customFieldDtos.add(selectField(1486845, cliente.getRegiao() == null ? 1242469 : cliente.getSegmento().getIdCrm()));

        Map<String, Integer> tagItem = conversa.getInativa()
                ? Map.of("id", 111143)
                : Map.of("id", 117527);

        Map<String, Object> embedded = Map.of("tags", List.of(tagItem));

        CardDto cardDto = CardDto.builder()
                .responsibleUserId(vendedor.getIdVendedorCrm())
                .statusId(93572343)
                .customFieldsValues(customFieldDtos)
                .embedded(embedded)
                .build();

        log.info("Body para atualizar card criado com sucesso. Body: {}", cardDto);

        try {
            var json = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cardDto);
            log.info("Kommo PATCH body=\n{}", json);
        } catch (Exception ignore) {
        }

        gateway.atualizarCard(cardDto, idLead);

        log.info("Atualização do crm concluída com sucesso. Card: {}, Id do lead: {}", cardDto, idLead);
    }

    public Integer consultaLeadPeloTelefone(String telefone) {
        log.info("Consultando lead pelo telefone. Telefone: {}", telefone);
        Optional<Integer> lead = gateway.consultaLeadPeloTelefone(telefone);

        if (lead.isEmpty()) {
            throw new LeadNaoEncontradoException();
        }

        log.info("Lead consultado com sucesso. Lead: {}", lead.get());
        return lead.get();
    }

    private CustomFieldDto textField(int fieldId, Object value) {
        return CustomFieldDto.builder()
                .fieldId(fieldId)
                .values(List.of(CustomFieldValueDto.builder()
                        .value(value)
                        .build()))
                .build();
    }

    private CustomFieldDto selectField(int fieldId, Integer... enumIds) {
        var list = java.util.Arrays.stream(enumIds)
                .map(id -> CustomFieldValueDto.builder()
                        .enumId(id)
                        .build())
                .toList();
        return CustomFieldDto.builder()
                .fieldId(fieldId)
                .values(list)
                .build();
    }
}
