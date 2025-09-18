package com.ai.kalauz.ai_kalauz_be.service;

import com.ai.kalauz.ai_kalauz_be.DTO.CourseDTO;
import com.ai.kalauz.ai_kalauz_be.DTO.LessonDTO;
import com.ai.kalauz.ai_kalauz_be.model.Course;
import com.ai.kalauz.ai_kalauz_be.model.Lesson;
import com.ai.kalauz.ai_kalauz_be.repository.CourseRepository;
import com.ai.kalauz.ai_kalauz_be.repository.LessonRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    //TODO refactor it, add the LessonService and use there the repository
    private final LessonRepository lessonRepository;
    private final ProgressService progressService;

    @Autowired
    public CourseService(
            CourseRepository courseRepository,
            LessonRepository lessonRepository,
            ProgressService progressService){
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.progressService = progressService;
    }

    public List<CourseDTO> getCourses(){
        List<Course> courses = this.courseRepository.findAll();

        return courses.stream()
                .map(course -> new CourseDTO(course.getId(), course.getName(), course.getDescription()))
                .collect(Collectors.toList());
    }

    public List<LessonDTO> getCourseLessons(Long id, String userAgent){
        System.out.println("service getCourseLesson" + id + userAgent);
        List<Lesson> lessons = this.lessonRepository.findByCourseId(id);

        System.out.println("lessons size: " + (lessons == null ? "null" : lessons.size()));

        Optional<Course> course = this.courseRepository.findById(id);

        if(course.isPresent()) {
            Cookie cookie = new Cookie(course.get().getName(), course.get().getDescription());

            System.out.println("cookie: " + cookie.getName());
        }else{
            System.out.println("ide jon");

        }
        return lessons.stream()
                .map(lesson -> new LessonDTO(lesson.getId(),lesson.getName(), lesson.getContent()))
                .collect(Collectors.toList());
    }

    public Optional<Course> getSelectedCourse(Long id){
        return this.courseRepository.findById(id);
    }

    public Integer getCourseProgress(Long id, HttpServletRequest request){
        return this.progressService.getCourseProgress(id, request);
    }
}
