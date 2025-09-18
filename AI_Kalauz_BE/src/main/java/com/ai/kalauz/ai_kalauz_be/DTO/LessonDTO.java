package com.ai.kalauz.ai_kalauz_be.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LessonDTO {

    public Long id;

    private String name;

    private String content;
}
