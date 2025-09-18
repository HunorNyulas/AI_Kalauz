package com.ai.kalauz.ai_kalauz_be.controller;

import com.ai.kalauz.ai_kalauz_be.DTO.LessonContentDTO;
import com.ai.kalauz.ai_kalauz_be.DTO.ProgressDTO;
import com.ai.kalauz.ai_kalauz_be.service.LessonService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "localhost:3000")
@RequestMapping("api/lesson")
public class LessonController {

    private LessonService lessonService;

    LessonController(LessonService lessonService){
        this.lessonService = lessonService;
    }

    @GetMapping("/content")
    public List<LessonContentDTO> getLessonContent(@RequestParam Long id, HttpServletResponse response){
        System.out.println("lesson id: " + id);
        Cookie cookie = new Cookie("lessonID", id.toString());
        cookie.setPath("/");
        cookie.setMaxAge(7200);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);

        response.addCookie(cookie);

        return this.lessonService.getLessonsContentById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProgress(@RequestBody Map<String, Object> body, HttpServletRequest request){
        System.out.println("id" + (Integer) body.get("lessonId"));
        //TODO need to change it to DTO
        Long lessonID = ((Number) body.get("lessonId")).longValue(); // ✅ works for Integer or Long
        this.lessonService.saveProgress(request, lessonID);
        return ResponseEntity.ok("saved progress successfully");
    }
}
