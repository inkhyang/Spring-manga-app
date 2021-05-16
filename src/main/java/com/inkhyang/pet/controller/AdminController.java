package com.inkhyang.pet.controller;

import com.inkhyang.pet.models.Role;
import com.inkhyang.pet.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/usersList";
    }
    @GetMapping("/admin/users/{username}")
    public String userEditForm(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("roles", Role.values());

        return "admin/userEdit";
    }

    /*@PostMapping("/admin/users/{username}")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("myUser") MyUser myUser) {

        myUser.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        myUser.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                myUser.getRoles().add(Role.valueOf(key));
            }
        }

        myUserRepository.save(myUser);

        return "redirect:/user";
    }*/
}
