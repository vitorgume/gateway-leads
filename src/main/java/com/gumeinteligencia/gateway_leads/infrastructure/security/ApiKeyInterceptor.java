package com.gumeinteligencia.gateway_leads.infrastructure.security;

import com.gumeinteligencia.gateway_leads.infrastructure.exceptions.ApiKeyInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${security.api.key}")
    private final String API_KEY;

    public ApiKeyInterceptor(
            @Value("${security.api.key}") String API_KEY
    ) {
        this.API_KEY = API_KEY;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String path = request.getRequestURI();

        // Whitelist rápida
        if (path.equals("/") ||
                path.equals("/error") ||
                path.equals("/health") ||
                path.startsWith("/actuator/") ||
                path.startsWith("/public/") ||
                path.startsWith("/webhook/")) {
            return true;
        }

        String receivedKey = request.getHeader("x-api-key");

        if (API_KEY.equals(receivedKey)) {
            return true;
        }

        throw new ApiKeyInvalidaException();
    }
}
