/*
package com.inkhyang.pet.controller;

import com.inkhyang.pet.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public String getProfilePage(@PathVariable String username, Model model) {
        userService.profilePage(username, model);
        return "user/profile-page";
    }

    @GetMapping("/user/{username}/settings")
    public String getProfileSettings(@PathVariable String username, Model model) {
        userService.profileSettings(username, model);
        return "user/profile-settings";
    }



    @PostMapping("/user/{username}/settings")
    public String profileSettingsEdit(@PathVariable String username,
                                      @RequestParam MultipartFile file,
                                      @RequestParam String newUsername) {
        userService.profileSettingsUpgrade(username, file, newUsername);
        return "redirect:/";
    }
}*/
