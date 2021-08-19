package com.prison.project.service.user;

import com.prison.project.model.Role;
import com.prison.project.model.User;
import com.prison.project.repository.UserRepository;
import com.prison.project.service.role.GetRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveUserServiceTest {
    @InjectMocks
    SaveUserService saveUserService;
    @Mock
    public UserRepository userRepository;
    @Mock
    public  GetRoleService getRoleService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private Set<Role> getRolesUser() {
        Set<Role> user = new HashSet<>();
        user.add(new Role(1L, "ROLE_USER"));
        return user;
    }

    User user = new User(1L, "username", "parolesnav", true,
            null);

    @Test
    void saveUserWhenIsRole() {
        when(getRoleService.findByRoleNameIgnoreCase("ROLE_USER")).thenReturn(Optional.of(new Role(1L, "ROLE_USER")));
        when (passwordEncoder.encode(user.getPassword())).thenReturn("$2a$10$.g54npZpglNcSmIQEqeQmehcSR2VecRHBZ6OO6MtPSi08Nj96y1Z2");


        when(userRepository.save(user)).thenReturn(user);


        User userResult = saveUserService.saveUser(user);

        assertEquals(user.getRoles(),userResult.getRoles());
        assertEquals(user, userResult);

        verify(getRoleService).findByRoleNameIgnoreCase("ROLE_USER");
        verify (userRepository).save(user);
        verify(passwordEncoder).encode("parolesnav");

    }


    @Test
    void saveUserWhenNoRoleInDatabase() {
        when(getRoleService.findByRoleNameIgnoreCase("ROLE_USER")).thenReturn(Optional.of(new Role("ROLE_USER")));
        when (passwordEncoder.encode(user.getPassword())).thenReturn("$2a$10$.g54npZpglNcSmIQEqeQmehcSR2VecRHBZ6OO6MtPSi08Nj96y1Z2");


        when(userRepository.save(user)).thenReturn(user);


        User userResult = saveUserService.saveUser(user);

        assertEquals(user.getRoles(),userResult.getRoles());
        assertEquals(user, userResult);

        verify(getRoleService).findByRoleNameIgnoreCase("ROLE_USER");
        verify (userRepository).save(user);
        verify(passwordEncoder).encode("parolesnav");

    }

    @Test
    void isUserAlreadyPresentTrue() {
        when (userRepository.findByUserName(user.getUserName())).thenReturn(user);
        boolean foundUser = saveUserService.isUserAlreadyPresent(user);

        assertTrue(foundUser);
        verify(userRepository).findByUserName(user.getUserName());
    }

    @Test
    void isUserAlreadyPresentFalse() {
        when (userRepository.findByUserName(user.getUserName())).thenReturn(null);
        boolean foundUser = saveUserService.isUserAlreadyPresent(user);

        assertFalse(foundUser);
        verify(userRepository).findByUserName(user.getUserName());
    }
}