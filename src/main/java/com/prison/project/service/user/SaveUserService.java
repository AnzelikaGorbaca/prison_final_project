package com.prison.project.service.user;

import com.prison.project.model.Role;
import com.prison.project.model.User;
import com.prison.project.repository.RoleRepository;
import com.prison.project.repository.UserRepository;
import com.prison.project.service.role.GetRoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class SaveUserService {

    public final UserRepository userRepository;
    public final GetRoleService getRoleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role role = roleRepository.findByRoleNameIgnoreCase("ROLE_USER")
                .orElseGet(() -> new Role("ROLE_USER"));

        user.setPassword(encodedPassword);
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }

    public boolean isUserAlreadyPresent(User user) {
        boolean isUserAlreadyExists = false;
        User existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser != null) {
            isUserAlreadyExists = true;
        }
        return isUserAlreadyExists;
    }
}
