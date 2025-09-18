package com.ai.kalauz.ai_kalauz_be.controller;

import com.ai.kalauz.ai_kalauz_be.DTO.CourseDTO;
import com.ai.kalauz.ai_kalauz_be.DTO.LessonDTO;
import com.ai.kalauz.ai_kalauz_be.model.Course;
import com.ai.kalauz.ai_kalauz_be.model.Lesson;
import com.ai.kalauz.ai_kalauz_be.repository.CourseRepository;
import com.ai.kalauz.ai_kalauz_be.service.CourseService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "localhost:3000")
@RequestMapping(path = "/api/courses")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping("/getcourses")
    public List<CourseDTO> getCourses(){
        System.out.println("yo: " + this.courseService.getCourses());
        return this.courseService.getCourses();
    }

    @GetMapping("/lesson")
    public List<LessonDTO> getCourseLessons(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response){
        System.out.println("bejon");
        String userAgent = request.getHeader("User-Agent");

        Optional<Course> selectedCourse = this.courseService.getSelectedCourse(id);

        if(selectedCourse.isPresent()){
            Cookie cookie = new Cookie("courseID", selectedCourse.get().getId().toString());
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(7200);
            response.addCookie(cookie);

            System.out.println("KAAAAAAAAAAAAAA");
        }

        System.out.println("bejon:: " + courseService.getCourseLessons(id, userAgent));
        return this.courseService.getCourseLessons(id, userAgent);
    }

    @GetMapping("/lessonState")
    private Integer progressOfCourse(@RequestParam Long id, HttpServletRequest request){
        System.out.println("AHAAAAAAAAAA: " + id);
        return this.courseService.getCourseProgress(id, request);
    }
}
