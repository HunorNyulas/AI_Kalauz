package com.ai.kalauz.ai_kalauz_be.DTO;

import com.ai.kalauz.ai_kalauz_be.model.Course;
import com.ai.kalauz.ai_kalauz_be.model.Lesson;
import com.ai.kalauz.ai_kalauz_be.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ProgressDTO {

    private Long id;

    private User user;

    private Course course;

    private Lesson lesson;

    private int progress;
}
