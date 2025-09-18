package com.ai.kalauz.ai_kalauz_be.repository;

import com.ai.kalauz.ai_kalauz_be.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @Query("SELECT COALESCE(MAX(p.progress), 0) FROM Progress p WHERE p.course.id = :courseID AND p.user.id = :userID")
    Integer findProgressByCourseID(@Param("courseID") Long courseID,@Param("userID") Long userID);
}
