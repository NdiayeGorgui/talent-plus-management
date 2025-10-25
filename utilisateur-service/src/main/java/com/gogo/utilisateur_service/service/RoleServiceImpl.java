package com.gogo.utilisateur_service.service;

import com.gogo.utilisateur_service.model.Role;
import com.gogo.utilisateur_service.enums.RoleName;
import com.gogo.utilisateur_service.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByRole(RoleName roleName) {
        return roleRepository.findByRole(roleName);
    }
}
