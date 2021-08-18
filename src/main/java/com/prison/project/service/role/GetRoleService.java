package com.prison.project.service.role;

import com.prison.project.model.Role;
import com.prison.project.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class GetRoleService {

    private final RoleRepository roleRepository;

    public Role findByRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }

    public boolean chekIfIsAdmin(Set<Role> roles) {
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
