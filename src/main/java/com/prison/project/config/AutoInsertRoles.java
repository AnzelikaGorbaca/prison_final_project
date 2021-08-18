package com.prison.project.config;

import com.prison.project.model.Role;
import com.prison.project.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AutoInsertRoles implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        registerRole("ROLE_ADMIN");
        registerRole("ROLE_USER");
    }

    private void registerRole(String roleName) {
        Role role = roleRepository.findByRoleNameIgnoreCase(roleName)
                .orElseGet(() -> new Role(roleName));

        roleRepository.save(role);
    }
}
