package com.inkhyang.pet.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pst")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long number;
    @NotEmpty(message = "Title should not be empty")
    private String title;
    private String genres, fulltext;
    private String image;
    @OneToMany
    @CollectionTable(name = "pst_chptr", joinColumns = @JoinColumn(name = "pst_id"))
    private List<Chapter> chapters = new ArrayList<>();


    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
