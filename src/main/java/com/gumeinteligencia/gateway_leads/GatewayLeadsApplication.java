package com.gumeinteligencia.gateway_leads;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GatewayLeadsApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("URL_BD", dotenv.get("URL_BD"));
		System.setProperty("USER_BD", dotenv.get("USER_BD"));
		System.setProperty("PASSWORD_BD", dotenv.get("PASSWORD_BD"));
		System.setProperty("WHASTAPP_CLIENT_TOKEN", dotenv.get("WHASTAPP_CLIENT_TOKEN"));
		System.setProperty("WHASTAPP_TOKEN", dotenv.get("WHASTAPP_TOKEN"));
		System.setProperty("WHASTAPP_INSTANCE_ID", dotenv.get("WHASTAPP_INSTANCE_ID"));
		System.setProperty("FINANCEIRO_TELEFONE", dotenv.get("FINANCEIRO_TELEFONE"));
		System.setProperty("LOGISTICA_TELEFONE", dotenv.get("LOGISTICA_TELEFONE"));
		System.setProperty("GERENCIA_TELEFONE", dotenv.get("GERENCIA_TELEFONE"));
		System.setProperty("CONSULTOR_TELEFONE", dotenv.get("CONSULTOR_TELEFONE"));

		SpringApplication.run(GatewayLeadsApplication.class, args);
	}

}
