package com.ai.kalauz.ai_kalauz_be.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
}
