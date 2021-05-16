package com.inkhyang.pet.repo;

import com.inkhyang.pet.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    boolean existsByChapterId(String chapterId);
}
