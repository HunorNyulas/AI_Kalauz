package com.ai.kalauz.ai_kalauz_be.service;

import com.ai.kalauz.ai_kalauz_be.exception.CourseNotFoundException;
import com.ai.kalauz.ai_kalauz_be.exception.LessonNotFoundException;
import com.ai.kalauz.ai_kalauz_be.model.Course;
import com.ai.kalauz.ai_kalauz_be.model.Lesson;
import com.ai.kalauz.ai_kalauz_be.model.Progress;
import com.ai.kalauz.ai_kalauz_be.model.User;
import com.ai.kalauz.ai_kalauz_be.repository.CourseRepository;
import com.ai.kalauz.ai_kalauz_be.repository.LessonRepository;
import com.ai.kalauz.ai_kalauz_be.repository.ProgressRepository;
import com.ai.kalauz.ai_kalauz_be.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProgressService {

    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private ProgressRepository progressRepository;
    private LessonRepository lessonRepository;

    @Autowired
    ProgressService(
            CourseRepository courseRepository,
            UserRepository userRepository,
            ProgressRepository progressRepository,
            LessonRepository lessonRepository
    ){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.lessonRepository = lessonRepository;
    }

    public void saveProgress(Long userID, Long courseID, Long lessonID){
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new UsernameNotFoundException("Username was not found"));
        Course course = this.courseRepository.findById(courseID)
                .orElseThrow(() -> new CourseNotFoundException("Course was not found"));
        Lesson lesson = this.lessonRepository.findById(lessonID)
                .orElseThrow(() -> new LessonNotFoundException("Lesson was not found"));

        Progress progress = new Progress(user,course,lesson,1);

        this.progressRepository.save(progress);
    }

    public Integer getCourseProgress(Long courseID, HttpServletRequest request){


        Cookie[] cookies = request.getCookies();
        Map<String, String> cookieMap = new HashMap<>();

        System.out.println("ITT HAL BE:" + Arrays.toString(cookies));

        if(cookies!=null){
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }

        System.out.println("response: " + this.progressRepository.findProgressByCourseID(courseID, Long.parseLong(cookieMap.get("userID"))));

        return this.progressRepository.findProgressByCourseID(courseID, Long.parseLong(cookieMap.get("userID")));
    }
}
