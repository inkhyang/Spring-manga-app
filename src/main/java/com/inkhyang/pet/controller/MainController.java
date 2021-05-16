package com.inkhyang.pet.controller;

import com.inkhyang.pet.models.User;
import com.inkhyang.pet.models.Post;
import com.inkhyang.pet.repo.PostRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@Controller
public class MainController {
    private final PostRepository postRepository;

    public MainController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/manga")
    public String allManga(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "manga/main-manga";
    }

    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("profile", user);
        model.addAttribute("frontendData", data);
        return "main-page";
    }
}
