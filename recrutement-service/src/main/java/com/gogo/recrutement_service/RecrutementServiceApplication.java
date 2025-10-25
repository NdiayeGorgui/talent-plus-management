package com.gogo.recrutement_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecrutementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecrutementServiceApplication.class, args);
	}
/* @Bean
    public CommandLineRunner initAdmin(AdminRepository adminRepository) {
        return args -> {
            String adminEmail = "admin@talentplus.com";

            boolean adminExists = adminRepository.findByEmail(adminEmail).isPresent();

            if (!adminExists) {
                Admin admin = new Admin();
                admin.setNom("Admin");
                admin.setPrenom("TalentPlus");
                admin.setEmail(adminEmail);
                admin.setTelephone("0000000000");
                admin.setPoste("Administrateur");

                adminRepository.save(admin);
                System.out.println("✅ Admin créé avec succès : " + adminEmail);
            } else {
                System.out.println("ℹ️ Admin déjà existant : " + adminEmail);
            }
        };
    }*/
}
