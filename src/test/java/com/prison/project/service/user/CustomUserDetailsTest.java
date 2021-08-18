package com.prison.project.service.user;

import com.prison.project.model.Role;
import com.prison.project.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomUserDetailsTest {


    private Set<Role> getRoles() {
        Set<Role> user = new HashSet<>();
        user.add(new Role(1L, "SITE_USER"));
        return user;
    }

    User user = new User(1L, "username", "123", true,
            getRoles());

    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    @Test
    void getAuthorities() {


        Set<Role> roles = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        Collection<? extends GrantedAuthority> authoritiesResult = customUserDetails.getAuthorities();
        assertEquals(authorities, authoritiesResult);
    }


    @Test
    void getPassword() {
        String result = customUserDetails.getPassword();
        String password = user.getPassword();
        assertEquals(password, result);
    }

    @Test
    void getUsername() {
        String result = customUserDetails.getUsername();
        String username = user.getUserName();
        assertEquals(username,result);
    }


    @Test
    void isAccountNonExpired() {
        boolean result = customUserDetails.isAccountNonExpired();
        assertTrue(result);
    }

    @Test
    void isAccountNonLocked() {
        boolean result = customUserDetails.isAccountNonLocked();
        assertTrue(result);
    }

    @Test
    void isCredentialsNonExpired() {
        boolean result = customUserDetails.isCredentialsNonExpired();
        assertTrue(result);
    }

    @Test
    void isEnabled() {
        boolean result = user.isEnabled();
        assertTrue(result);
    }

}