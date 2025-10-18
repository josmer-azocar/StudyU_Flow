package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DegreeRequestDto(

        @NotBlank(message = "Degree name cant be empty.")
        @Size(max = 100, message = "Degree name cannot exceed 100 characters.")
        String name


) {
}
