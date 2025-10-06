package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @NotBlank(message = "Username is required.")
        @Size(max = 100, message = "Username cannot exceed 100 characters.")
        String username,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        String password,

        @NotBlank(message = "First name is required.")
        @Size(max = 25, message = "First name cannot exceed 25 characters.")
        String firstName,

        @NotBlank(message = "Last name is required.")
        @Size(max = 25, message = "Last name cannot exceed 25 characters.")
        String lastName
) {
}
