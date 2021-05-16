package com.inkhyang.pet.service;

import com.inkhyang.pet.models.Chapter;
import com.inkhyang.pet.models.Post;
import com.inkhyang.pet.repo.ChapterRepository;
import com.inkhyang.pet.repo.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ChapterService {
    private final PostRepository postRepository;
    private final ChapterRepository chapterRepository;

    public ChapterService(PostRepository postRepository, ChapterRepository chapterRepository) {
        this.postRepository = postRepository;
        this.chapterRepository = chapterRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;


    public String checkChapterIdExist(String chapterId) {
        return chapterRepository.existsByChapterId(chapterId) ? "VALID" : "NO_VALID";
    }

    public void saveChapterToDB(String title, String chapterId, MultipartFile[] files) {
        Post post = postFindByTitle(title);
        Chapter chapter = new Chapter();
        List<String> imgs = new ArrayList<>();
        Arrays.stream(files).forEach(file -> imgs.add(fileToStringUpload(file)));
        chapter.setImages(imgs);

        chapter.setChapterId(chapterId);
        chapter.setDate(new Date());
        post.getChapters().add(chapter);
        chapterRepository.save(chapter);
    }

    public void getPostChaptersTable(String title, Model model) {
        Post post = postFindByTitle(title);
        Iterable<Chapter> chapters = post.getChapters().stream()
                .sorted(Comparator.comparing(Chapter::getDate).reversed())
                .collect(Collectors.toList());
        model.addAttribute("p_chapters", chapters);
    }

    public void getChapterDetails(String title, String chapterId, Model model) {
        Post post = postFindByTitle(title);
        Chapter chapter = getChapterOptional(post, chapterId).get();
        Iterable<String> res = chapter.getImages();
        model.addAttribute("ch_Details", res);
    }

    public void getChapterForEdit(String title, String chapterId, Model model) {
        Post post = postFindByTitle(title);
        Optional<Chapter> chapter = getChapterOptional(post, chapterId);
        Iterable<String> resImg = chapter.get().getImages();
        model.addAttribute("ch_EditImg", resImg);
        List<Chapter> res = new ArrayList<>();
        chapter.ifPresent(res::add);
        model.addAttribute("ch_Edit", res);
    }

    public void chapterEdit(String title, String chapterId, String newChapterId, MultipartFile[] files) {
        Post post = postFindByTitle(title);
        Chapter chapter = getChapterOptional(post, chapterId).get();
        List<String> imgsToDelete = chapter.getImages();
        imgsToDelete.stream().forEach(x -> {
            try {
                Files.deleteIfExists(Path.of(uploadPath, x));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        List<String> imgs = new ArrayList<>();
        Arrays.stream(files).forEach(file -> imgs.add(fileToStringUpload(file)));
        chapter.setImages(imgs);

        chapter.setChapterId(newChapterId);
        chapter.setDate(new Date());
        post.getChapters().removeIf(x -> x.equals(chapter));
        post.getChapters().add(chapter);
        chapterRepository.save(chapter);
    }

    public void chapterRemove(String title, String chapterId) {
        Post post = postFindByTitle(title);
        Chapter chapter = getChapterOptional(post, chapterId).get();
        List<String> imgs = chapter.getImages();
        imgs.stream().forEach(x -> {
            try {
                Files.deleteIfExists(Path.of(uploadPath, x));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        post.getChapters().removeIf(x -> x.getChapterId().equals(chapterId));
        chapterRepository.delete(chapter);
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

    public Optional<Chapter> getChapterOptional(Post post, String chapterId) {
        return post.getChapters().stream().filter(x -> x.getChapterId().equals(chapterId)).findAny();
    }

    public Post postFindByTitle(String title) {
        return postRepository.findByTitle(title).orElseGet(null);
    }
}
