package com.inkhyang.pet.service;

import com.inkhyang.pet.models.Post;
import com.inkhyang.pet.repo.ChapterRepository;
import com.inkhyang.pet.repo.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class MangaService {

    @Value("${upload.path}")
    private String uploadPath;

    private final PostRepository postRepository;
    private final ChapterRepository chapterRepository;

    public MangaService(PostRepository postRepository, ChapterRepository chapterRepository) {
        this.postRepository = postRepository;
        this.chapterRepository = chapterRepository;
    }

    public String checkTitleExist(String title) {
        return postRepository.existsByTitle(title) ? null : "redirect:/manga";
    }

    public void saveMangaToDB(MultipartFile file, String title, String fulltext, String genres) {

        Post post = new Post();

        post.setImage(fileToStringUpload(file));

        post.setTitle(title);
        post.setFulltext(fulltext);
        post.setGenres(genres);

        postRepository.save(post);
    }

    public void getMangaDetails(String title, Model model) {
        Optional<Post> optional = postRepository.findByTitle(title);
        List<Post> res = new ArrayList<>();
        optional.ifPresent(res::add);
        model.addAttribute("p_details", res);
    }

    public void getMangaForEdit(String title, Model model) {
        Optional<Post> optional = postRepository.findByTitle(title);
        List<Post> res = new ArrayList<>();
        optional.ifPresent(res::add);
        model.addAttribute("p_edit", res);
    }

    public void mangaPostEdit(String newTitle, String genres, String fulltext, MultipartFile file, String title) {
        Post post = postRepository.findByTitle(title).orElseThrow();
        post.setTitle(newTitle);
        post.setGenres(genres);
        post.setFulltext(fulltext);
        try {
            Files.deleteIfExists(Path.of(uploadPath + post.getImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.setImage(fileToStringUpload(file));

        postRepository.save(post);
    }

    public void mangaPostRemove(String title) {
        Post post = postRepository.findByTitle(title).orElseThrow();

        try {
            Files.deleteIfExists(Path.of(uploadPath, post.getImage()));
            post.getChapters()
                    .stream().forEach(ch -> ch.getImages()
                    .forEach(c -> {
                        try {
                            Files.deleteIfExists(Path.of(uploadPath, c));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        chapterRepository.deleteAll(post.getChapters());
        postRepository.delete(post);
    }

    public String fileToStringUpload(MultipartFile file) {
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
}