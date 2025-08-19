package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class EscolhaVendedor  {
    private boolean roleta;
    private String vendedor;
}
