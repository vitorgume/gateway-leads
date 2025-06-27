package com.gumeinteligencia.gateway_leads.entrypoint.controller.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImageDto {
    private String imageUrl;
    private String caption;
}
