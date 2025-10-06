package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.LoginRequestDto;
import com.api.StudyU_Flow.domain.dto.request.RegisterRequestDto;
import com.api.StudyU_Flow.domain.dto.response.AuthResponseDto;
import com.api.StudyU_Flow.jwt.JwtService;
import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import com.api.StudyU_Flow.persistence.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentRepository studentRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(StudentRepository studentRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto login(LoginRequestDto requestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password()));
        UserDetails user = studentRepository.findByUsername(requestDto.username()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponseDto.builder()
                .token(token)
                .build();
    }

    public AuthResponseDto register(RegisterRequestDto requestDto) {
        StudentEntity student = StudentEntity.builder()
                .username(requestDto.username())
                .password(passwordEncoder.encode(requestDto.password()))
                .firstName(requestDto.firstName())
                .lastName(requestDto.lastName())
                .build();

        studentRepository.save(student);

        return AuthResponseDto.builder().token(jwtService.getToken(student)).build();
    }


}
