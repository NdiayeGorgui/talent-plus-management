package com.gogo.offre_emploi_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class OffreEmploiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffreEmploiServiceApplication.class, args);
	}

}
