package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.LoginRequestDto;
import com.api.StudyU_Flow.domain.dto.request.RegisterRequestDto;
import com.api.StudyU_Flow.domain.dto.response.AuthResponseDto;
import com.api.StudyU_Flow.domain.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication functions")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * @description
     * ES: Endpoint para loggear Usuarios registrados.
     * EN: Endpoint to log in a registered User.
     */
    @PostMapping(value = "login")
    @Operation(
            summary = "Allow to log in a User by his username and password",
            description = "return LoginRequestDto (Record Object)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged-in successfully"),
            }
    )
    public ResponseEntity<AuthResponseDto> login(@Parameter(description = "Username and password",
                                                            example = "{\n" +
                                                                    "    \"username\" : \"UserNameExample\",\n" +
                                                                    "    \"password\" : \"0123456789\"\n" +
                                                                    "}")
                                                     @RequestBody
                                                     @Valid LoginRequestDto requestDto){

        return ResponseEntity.ok(authService.login(requestDto));
    }

    /**
     * @description
     * ES: Endpoint para registrar Usuarios nuevos.
     * EN: Endpoint to register a new User.
     */
    @PostMapping(value = "register")
    @Operation(
            summary = "Allow to register a new User by an username, password, first name and last name.",
            description = "return RegisterRequestDto (Record Object)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "new User registered successfully"),
            }
    )
    public ResponseEntity<AuthResponseDto> register( @Parameter(description = "Username, password, first name, and last name",
                                                                example = "{\n" +
                                                                    "    \"username\" : \"UserNameExample\",\n" +
                                                                    "    \"password\" : \"0123456789\"\n" +
                                                                    "    \"firstName\" : \"FirstNameExample\"\n" +
                                                                    "    \"lastName\" : \"LastNameExample\"\n" +
                                                                    "}")
                                                         @RequestBody
                                                         @Valid RegisterRequestDto requestDto){

        return ResponseEntity.ok(authService.register(requestDto));
    }
}
