package com.ai.kalauz.ai_kalauz_be.service;

import com.ai.kalauz.ai_kalauz_be.exception.InvalidCredentials;
import com.ai.kalauz.ai_kalauz_be.exception.UsernameAlreadyExistsException;
import com.ai.kalauz.ai_kalauz_be.model.User;
import com.ai.kalauz.ai_kalauz_be.repository.UserRepository;
import com.ai.kalauz.ai_kalauz_be.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public String loginUser(String username, String password) {

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new InvalidCredentials("Invalid username");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentials("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

}
