package com.prison.project.service.user;

import com.prison.project.model.Roles;
import com.prison.project.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomUserDetailsTest {

//
//    private Set<Roles> getRoles() {
//        Set<Roles> user = new HashSet<>();
//        user.add(new Roles(1L, "SITE_USER"));
//        return user;
//    }
//
//    User user = new User(1L, "username", "123", true,
//            getRoles());
//
//    CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//    @Test
//    void getAuthorities() {
//
//
//        Set<Roles> roles = getRoles();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (Roles role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        }
//
//        Collection<? extends GrantedAuthority> authoritiesResult = customUserDetails.getAuthorities();
//        assertEquals(authorities, authoritiesResult);
//    }
//
//
//    @Test
//    void getPassword() {
//        String result = customUserDetails.getPassword();
//
//        assertNull(result);
//    }
//
//    @Test
//    void getUsername() {
//        String result = customUserDetails.getUsername();
//
//        assertNull(result);
//    }
//
//
//    @Test
//    void isAccountNonExpired() {
//        boolean result = customUserDetails.isAccountNonExpired();
//        assertFalse(result);
//    }
//
//    @Test
//    void isAccountNonLocked() {
//        boolean result = customUserDetails.isAccountNonLocked();
//        assertFalse(result);
//    }
//
//    @Test
//    void isCredentialsNonExpired() {
//        boolean result = customUserDetails.isCredentialsNonExpired();
//        assertFalse(result);
//    }
//
//    @Test
//    void isEnabled() {
//        boolean result = customUserDetails.isEnabled();
//        assertTrue(result);
//    }

}