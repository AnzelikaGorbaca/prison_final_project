package com.prison.project.service.role;

import com.prison.project.model.Role;
import com.prison.project.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRoleServiceTest {
    @InjectMocks
    GetRoleService getRoleService;
    @Mock
    RoleRepository roleRepository;

    private final String roleName = "ROLE_USER";

    @Test
    void findByRoleNameIgnoreCaseWhenThereIsRole() {

        when(roleRepository.findByRoleNameIgnoreCase(roleName)).thenReturn(Optional.of(new Role(1L, "ROLE_USER")));
        Optional<Role> roleResult = getRoleService.findByRoleNameIgnoreCase(roleName);

        assertEquals(Optional.of(new Role(1L, "ROLE_USER")), roleResult);
        verify(roleRepository).findByRoleNameIgnoreCase(roleName);
    }


    @Test
    void findByRoleNameIgnoreCaseWhenThereIsNoRole() {

        when(roleRepository.findByRoleNameIgnoreCase(roleName)).thenReturn(Optional.of(new Role("ROLE_USER")));
        Optional<Role> roleResult = getRoleService.findByRoleNameIgnoreCase(roleName);

        assertEquals(Optional.of(new Role("ROLE_USER")), roleResult);
        verify(roleRepository).findByRoleNameIgnoreCase(roleName);
    }
}