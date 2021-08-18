package com.prison.project.repository;


import com.prison.project.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findByRoleName (String role);


}
