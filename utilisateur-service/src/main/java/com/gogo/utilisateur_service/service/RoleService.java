package com.gogo.utilisateur_service.service;


import com.gogo.utilisateur_service.model.Role;
import com.gogo.utilisateur_service.enums.RoleName;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRole(RoleName roleName);
}

