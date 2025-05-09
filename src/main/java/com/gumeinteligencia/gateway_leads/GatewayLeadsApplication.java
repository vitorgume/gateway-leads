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
		System.setProperty("WHATSAPP_CHAVE_API", dotenv.get("WHASTAPP_CHAVE_API"));

		SpringApplication.run(GatewayLeadsApplication.class, args);
	}

}
