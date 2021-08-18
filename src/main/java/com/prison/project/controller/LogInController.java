package com.prison.project.controller;

import com.prison.project.model.User;
import com.prison.project.repository.UserRepository;
import com.prison.project.service.role.GetRoleService;
import com.prison.project.service.user.CustomUserDetails;
import com.prison.project.service.user.CustomUserDetailsService;
import com.prison.project.service.user.SaveUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;


@Controller
@AllArgsConstructor
public class LogInController {

    private final SaveUserService saveUserService;
    private final GetRoleService getRoleService;

    @GetMapping
    public String mainIndex(Model model) {
        return "main";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(User user, Model model) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (saveUserService.isUserAlreadyPresent(user)) {
            model.addAttribute("message", "User Already Exists!");
             return showSignUpForm(model);
        } else {
            saveUserService.saveUser(user);
        }
        return "register_success";
    }


    @PostMapping("/login")
    public String logIn(User user) {
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUserName());
//        if (getRoleService.chekIfIsAdmin(user.getRoles())){
//            return "admin-page";
//        }
        return "redirect:/prison-management-system";
    }


}
