package com.ai.kalauz.ai_kalauz_be.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="Courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "course")
    private List<Progress> progress;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;
}
