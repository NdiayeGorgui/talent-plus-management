package com.gogo.utilisateur_service.repository;

import com.gogo.utilisateur_service.enums.RoleName;
import com.gogo.utilisateur_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole(RoleName roleName);
}
