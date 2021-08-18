package com.prison.project.service.user;

import com.prison.project.model.Roles;
import com.prison.project.model.User;
import com.prison.project.repository.UserRepository;
import com.prison.project.service.role.GetRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class SaveUserService {

    public final UserRepository userRepository;
    public final GetRoleService getRoleService;

    public User saveUser (User user) {
        Roles userRole = getRoleService.findByRoleName("SITE_USER");
        user.setRoles (new HashSet<Roles>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public boolean isUserAlreadyPresent(User user) {
        boolean isUserAlreadyExists = false;
        User existingUser = userRepository.findByUserName(user.getUserName());
        if(existingUser != null){
            isUserAlreadyExists = true;
        }
        return isUserAlreadyExists;
    }
}
