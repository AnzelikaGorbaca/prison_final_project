package com.prison.project.service.role;

import com.prison.project.model.Roles;
import com.prison.project.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class GetRoleService {

    private final RoleRepository roleRepository;

    public Roles findByRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }

    public boolean chekIfIsAdmin(Set<Roles> roles) {
        String role = null;
        if (roles.stream().findFirst().isPresent()) {
            role = roles.stream().findFirst().get().getRoleName();
        }
        System.out.println(role);
        if (role.contains("ADMIN")) {
            return true;
        }
        return false;
    }
}
