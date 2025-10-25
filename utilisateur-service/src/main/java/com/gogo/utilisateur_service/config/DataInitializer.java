package com.gogo.utilisateur_service.config;

import com.gogo.utilisateur_service.enums.RoleName;
import com.gogo.utilisateur_service.model.Role;
import com.gogo.utilisateur_service.model.Utilisateur;
import com.gogo.utilisateur_service.repository.RoleRepository;
import com.gogo.utilisateur_service.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            // ✅ 1️⃣ Créer les rôles seulement s’ils n’existent pas
            for (RoleName roleName : RoleName.values()) {
                if (roleRepository.findByRole(roleName).isEmpty()) {
                    Role role = new Role();
                    role.setRole(roleName);
                    roleRepository.save(role);
                    System.out.println("✅ Rôle créé : " + roleName);
                }
            }

            // ✅ 2️⃣ Créer un admin seulement s’il n’existe pas
            if (utilisateurRepository.findByEmail("admin@talentplus.com").isEmpty()) {
                Utilisateur admin = new Utilisateur();
                admin.setUsername("admin");
                admin.setEmail("admin@talentplus.com");
                admin.setPassword(passwordEncoder.encode("admin123"));

                Role adminRole = roleRepository.findByRole(RoleName.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Rôle ADMIN introuvable"));

                admin.setRoles(List.of(adminRole));
                utilisateurRepository.save(admin);

                System.out.println("👑 Admin créé : admin@talentplus.com / admin123");
            } else {
                System.out.println("ℹ️ Admin déjà existant, aucune création.");
            }
        };
    }
}
