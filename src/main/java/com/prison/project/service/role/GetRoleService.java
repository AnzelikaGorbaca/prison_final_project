package com.prison.project.service.role;

import com.prison.project.model.Role;
import com.prison.project.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetRoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByRoleNameIgnoreCase(String roleName) {
        return roleRepository.findByRoleNameIgnoreCase(roleName);
    }

}
