package com.prison.project.service.user;

import com.prison.project.model.Role;
import com.prison.project.model.User;
import com.prison.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        String username = "username";
        Set<Role> admin = new HashSet<>();
        admin.add(new Role(1L, "ADMIN"));

        User user = new User(1L, "username", "123", true,
                admin);
        when(userRepository.findByUserName(username)).thenReturn(user);

        UserDetails customUserDetails = new CustomUserDetails(user);
        UserDetails customUserDetailsResult = customUserDetailsService.loadUserByUsername(username);

        assertEquals(customUserDetails, customUserDetailsResult);
        verify(userRepository).findByUserName(username);
    }

    @Test
    void loadUserByUsernameWhenNull() {
        String username = "username";
        when(userRepository.findByUserName(username)).thenReturn(null);

        try {
            customUserDetailsService.loadUserByUsername(username);
            fail();
        } catch (UsernameNotFoundException e) {
            assertEquals(e.getMessage(), "User not found");
        }
        verify(userRepository).findByUserName(username);
    }
}