package com.inkhyang.pet.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chptr")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long index;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @ElementCollection
    private List<String> images;
    private String chapterId;


    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
