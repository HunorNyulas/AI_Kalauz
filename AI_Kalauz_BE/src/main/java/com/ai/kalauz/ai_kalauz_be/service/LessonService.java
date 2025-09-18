package com.ai.kalauz.ai_kalauz_be.service;

import com.ai.kalauz.ai_kalauz_be.DTO.LessonContentDTO;
import com.ai.kalauz.ai_kalauz_be.log.RequestLogger;
import com.ai.kalauz.ai_kalauz_be.model.LessonContent;
import com.ai.kalauz.ai_kalauz_be.model.Progress;
import com.ai.kalauz.ai_kalauz_be.repository.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonContentRepository lessonContentRepository;
    private final RequestLogger requestLogger;
    private final ProgressService progressService;


    @Autowired
    LessonService(
            LessonContentRepository lessonContentRepository,
            RequestLogger requestLogger,
            ProgressService progressService
    ){
        this.lessonContentRepository = lessonContentRepository;
        this.requestLogger = requestLogger;
        this.progressService = progressService;
    }

    public List<LessonContentDTO> getLessonsContentById(Long id){
        List<LessonContent> lessonContents = this.lessonContentRepository.findByLessonId(id);

        return lessonContents.stream()
                .map(lessonContent -> new LessonContentDTO(lessonContent.getId(), lessonContent.getLessonContent()))
                .collect(Collectors.toList());
    }

    public void saveProgress(HttpServletRequest request,Long id){

        this.requestLogger.logRequest(request);

        Cookie[] cookies = request.getCookies();
        Map<String, String> cookieMap = new HashMap<>();

        if (cookies == null) {
            throw new RuntimeException("No cookies found!");
        }else{

            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }

        this.progressService.saveProgress(Long.parseLong(cookieMap.get("userID")), Long.parseLong(cookieMap.get("courseID")), Long.parseLong(cookieMap.get("lessonID")));

    }
}
