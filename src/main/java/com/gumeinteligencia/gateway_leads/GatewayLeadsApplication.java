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
		System.setProperty("GCLOUD_ID_PROJETO", dotenv.get("GCLOUD_ID_PROJETO"));

		SpringApplication.run(GatewayLeadsApplication.class, args);
	}

}
