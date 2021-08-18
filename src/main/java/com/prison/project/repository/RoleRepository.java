package com.prison.project.repository;


import com.prison.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String role);

    Optional<Role> findByRoleNameIgnoreCase(String roleName);
}
