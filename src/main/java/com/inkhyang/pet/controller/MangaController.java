package com.inkhyang.pet.controller;


import com.inkhyang.pet.service.ChapterService;
import com.inkhyang.pet.service.MangaService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class MangaController {

    private final MangaService mangaService;
    private final ChapterService chapterService;

    public MangaController(MangaService mangaService, ChapterService chapterService) {
        this.mangaService = mangaService;
        this.chapterService = chapterService;
    }

    @GetMapping("/manga/add")
    public String mangaAdd() {
        return "manga/manga-add";
    }
    
    @PostMapping("/manga/add")
    public String mangaNewPost(@RequestParam("file") MultipartFile file,
                                @RequestParam String title, @RequestParam String genres,
                               @RequestParam String fulltext) {
        mangaService.saveMangaToDB(file, title, fulltext, genres);
        return "redirect:/manga";
    }

    @GetMapping("/manga/{title}")
    public String mangaDetails(@PathVariable(value= "title") String title, Model model) {
        mangaService.checkTitleExist(title);
        mangaService.getMangaDetails(title, model);
        chapterService.getPostChaptersTable(title, model);
        return "manga/manga-details";
    }

    @GetMapping("/manga/{title}/edit")
    public String getMangaEdit(@PathVariable(value= "title") String title, Model model) {
        mangaService.checkTitleExist(title);
        mangaService.getMangaForEdit(title, model);
        return "manga/manga-edit";
    }

    @PostMapping("/manga/{title}/edit")
    public String mangaPostUpdate(@RequestParam String newTitle, @RequestParam String newGenres,
                               @RequestParam String newFulltext, @RequestParam("file") MultipartFile newFile,
                                  @PathVariable(value= "title") String title) {
        mangaService.checkTitleExist(title);
        mangaService.mangaPostEdit(newTitle, newGenres, newFulltext, newFile, title);
        return "redirect:/manga";
    }

    @PostMapping("/manga/{title}/delete")
    public String mangaPostDelete(@PathVariable(value= "title") String title) {
        mangaService.checkTitleExist(title);
        mangaService.mangaPostRemove(title);
        return "redirect:/manga";
    }
    @GetMapping("/manga/{title}/chapterAdd")
    public String mangaChapterAdd(@PathVariable(value="title") String title) {
        mangaService.checkTitleExist(title);
        return "manga/chapter/chapter-add";
    }


    @PostMapping("/manga/{title}/chapterAdd")
    public String mangaNewChapter(@PathVariable(value="title") String title,
                                  @RequestParam String chapterId,
                                  @RequestParam("files") MultipartFile[] files) {
        mangaService.checkTitleExist(title);
        chapterService.saveChapterToDB(title, chapterId, files);
        return "redirect:/manga";
    }

    @GetMapping("/manga/{title}/chapters/{chapterId}")
    public String mangaChapterDetails(@PathVariable(value="title") String title,
                                      @PathVariable(value = "chapterId") String chapterId,
                                      Model model) {
        mangaService.checkTitleExist(title);
        chapterService.checkChapterIdExist(chapterId);
        chapterService.getChapterDetails(title, chapterId, model);
        return "manga/chapter/main-chapter";
    }

    @GetMapping("/manga/{title}/chapters/{chapterId}/edit")
    public String getChapterEdit(@PathVariable(value="title") String title,
                                 @PathVariable(value = "chapterId") String chapterId,
                                 Model model) {
        chapterService.getChapterForEdit(title, chapterId, model);
        return "manga/chapter/chapter-edit";
    }

    @PostMapping("/manga/{title}/chapters/{chapterId}/edit")
    public String chapterUpdate(@PathVariable(value="title") String title,
                                @PathVariable(value = "chapterId") String chapterId,
                                @RequestParam String newChapterId,
                                @RequestParam("files") MultipartFile[] files) {
        chapterService.chapterEdit(title, chapterId, newChapterId, files);
        return "redirect:/manga";
    }

    @PostMapping("/manga/{title}/chapters/{chapterId}/delete")
    public String chapterDelete(@PathVariable(value="title") String title,
                                @PathVariable(value = "chapterId") String chapterId) {
        chapterService.chapterRemove(title, chapterId);
        return "redirect:/manga";
    }
}