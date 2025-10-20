package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SubjectRequestDto(

        @NotBlank(message = "The subject name must not be blank.")
        @Size(max = 100, message = "The subject name must not exceed 100 characters.")
        String name,

        @NotNull(message = "The number of credits is required.")
        @Min(value = 0, message = "The subject must have at least 0 credit.")
        Integer credits,

        @Min(value = 1, message = "The semester must be at least 1.")
        Integer semester,

        @NotNull(message = "The student degree ID to which it belongs is required.")
        @Min(value = 1, message = "The student degree ID is not valid.")
        Long idStudentDegree
) {
}
