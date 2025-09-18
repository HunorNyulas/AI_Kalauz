package com.ai.kalauz.ai_kalauz_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LessonContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(columnDefinition = "TEXT", name = "content")
    private String lessonContent;

}
