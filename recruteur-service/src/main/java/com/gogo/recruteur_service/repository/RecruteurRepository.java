package com.gogo.recruteur_service.repository;


import com.gogo.recruteur_service.model.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruteurRepository extends JpaRepository<Recruteur, Long> {
    Optional<Recruteur> findByEmail(String email);
}

