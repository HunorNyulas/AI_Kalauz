package com.ai.kalauz.ai_kalauz_be.controller;

import com.ai.kalauz.ai_kalauz_be.DTO.AuthRequestDTO;
import com.ai.kalauz.ai_kalauz_be.DTO.JwtResponseDTO;
import com.ai.kalauz.ai_kalauz_be.model.User;
import com.ai.kalauz.ai_kalauz_be.security.JwtUtil;
import com.ai.kalauz.ai_kalauz_be.service.AuthService;

import com.ai.kalauz.ai_kalauz_be.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="localhost:3000")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(
            AuthService authService,
            JwtUtil jwtUtil,
            UserService userService)
    {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody AuthRequestDTO request){
        return authService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request, HttpServletRequest httpRequest, HttpServletResponse response){
        String token = authService.loginUser(request.getUsername(), request.getPassword());

        User user = this.userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username was not found"));

        //TODO make it for sessions

        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setPath("/"); // must match the path of the original cookie
                cookie.setMaxAge(0); // delete immediately
                response.addCookie(cookie);
            }
        }

        Cookie cookie = new Cookie("userID", user.getId().toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7200);

        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }
}
