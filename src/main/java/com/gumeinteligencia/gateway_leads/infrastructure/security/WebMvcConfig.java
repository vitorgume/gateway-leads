package com.gumeinteligencia.gateway_leads.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiKeyInterceptor apiKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",              // raiz
                        "/error",         // erro padrão do Spring
                        "/favicon.ico",   // se existir
                        "/actuator/**",   // actuator
                        "/health",        // se tiver esse endpoint também
                        "/public/**",     // públicos
                        "/webhook/**"     // webhooks
                );
    }
}
