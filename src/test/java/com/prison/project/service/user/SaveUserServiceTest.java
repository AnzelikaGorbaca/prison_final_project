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

import java.util.HashSet;
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
    public GetRoleService getRoleService;

    private Set<Role> getRolesUser() {
        Set<Role> user = new HashSet<>();
        user.add(new Role(1L, "SITE_USER"));
        return user;
    }

    User user = new User(1L, "username", "123", true,
            null);

    @Test
    void saveUser() {
        when(getRoleService.findByRoleName("SITE_USER")).thenReturn(new Role(1L, "SITE_USER"));
        when(userRepository.save(user)).thenReturn(user);

        User userResult = saveUserService.saveUser(user);
        assertEquals(getRolesUser(), user.getRoles());

        verify(getRoleService).findByRoleName("SITE_USER");
        verify (userRepository).save(user);

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