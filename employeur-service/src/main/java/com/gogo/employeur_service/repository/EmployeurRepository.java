package com.gogo.employeur_service.repository;


import com.gogo.employeur_service.model.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeurRepository extends JpaRepository<Employeur, Long> {
    Optional<Employeur> findByEmailContact(String email);
}
