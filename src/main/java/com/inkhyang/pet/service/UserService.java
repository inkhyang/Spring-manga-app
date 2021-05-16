/*
package com.inkhyang.pet.service;

import com.inkhyang.pet.models.MyUser;
import com.inkhyang.pet.repo.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final MyUserRepository myUserRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public UserService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    public void profilePage(String username, Model model) {
        UserDetails ud = loadUserByUsername(username);
        Optional<MyUser> optional = Optional.ofNullable(myUserRepository.findByUsername(username));
        List<MyUser> res = new ArrayList<>();
        optional.ifPresent(res::add);
        model.addAttribute("userDetails", res);
    }

    public void profileSettings(String username, Model model) {
        Optional<MyUser> optional = Optional.ofNullable(myUserRepository.findByUsername(username));
        List<MyUser> res = new ArrayList<>();
        optional.ifPresent(res::add);
        model.addAttribute("userSettings", res);
    }

    public void profileSettingsUpgrade(String username, MultipartFile file, String newUsername) {
        MyUser myUser = myUserRepository.findByUsername(username);
        try {
            Files.delete(Path.of(uploadPath + myUser.getAvatar()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myUser.setAvatar(fileToStringUpload(file));
        myUser.setUsername(newUsername);
    }

    private String fileToStringUpload(MultipartFile file) {
        String resultFilename = null;
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultFilename;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+ username);
        }
        UserDetails user = User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(myUser.getRoles().iterator().next().toString())
                .build();
        return user;
    }
}
*/
