package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.LoginRequestDto;
import com.api.StudyU_Flow.domain.dto.request.RegisterRequestDto;
import com.api.StudyU_Flow.domain.dto.response.AuthResponseDto;
import com.api.StudyU_Flow.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto requestDto){
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto requestDto){
        return ResponseEntity.ok(authService.register(requestDto));
    }
}
