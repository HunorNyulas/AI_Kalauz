package com.ai.kalauz.ai_kalauz_be.repository;

import com.ai.kalauz.ai_kalauz_be.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourseId(Long courseId);

}
