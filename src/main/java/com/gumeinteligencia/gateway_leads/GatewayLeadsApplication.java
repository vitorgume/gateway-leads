package com.gumeinteligencia.gateway_leads;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GatewayLeadsApplication {

	public static void main(String[] args) {
		System.setProperty("URL_BD", System.getenv("URL_BD"));
		System.setProperty("USER_BD", System.getenv("USER_BD"));
		System.setProperty("PASSWORD_BD", System.getenv("PASSWORD_BD"));
		System.setProperty("WHASTAPP_CLIENT_TOKEN", System.getenv("WHASTAPP_CLIENT_TOKEN"));
		System.setProperty("WHASTAPP_TOKEN", System.getenv("WHASTAPP_TOKEN"));
		System.setProperty("WHASTAPP_INSTANCE_ID", System.getenv("WHASTAPP_INSTANCE_ID"));

		SpringApplication.run(GatewayLeadsApplication.class, args);
	}

}
