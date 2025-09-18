package com.ai.kalauz.ai_kalauz_be.repository;

import com.ai.kalauz.ai_kalauz_be.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
