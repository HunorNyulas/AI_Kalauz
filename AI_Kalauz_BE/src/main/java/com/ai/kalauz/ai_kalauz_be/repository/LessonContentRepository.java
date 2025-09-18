package com.ai.kalauz.ai_kalauz_be.repository;

import com.ai.kalauz.ai_kalauz_be.DTO.LessonContentDTO;
import com.ai.kalauz.ai_kalauz_be.model.LessonContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonContentRepository extends JpaRepository<LessonContent, Long> {

    List<LessonContent> findByLessonId(Long id);

}
