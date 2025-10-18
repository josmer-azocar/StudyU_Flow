package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record StudentDegreeRequestDto(

        @NotNull(message = "The degree ID to which it belongs is required.")
        @Min(value = 1, message = "The degree ID is not valid.")
        Long idDegree,

        @NotNull(message = "The number of semesters is required.")
        @Min(value = 1, message = "The degree must have at least 1 semester.")
        Integer quantitySemesters,

        @NotNull(message = "The total credits are required.")
        @Min(value = 1, message = "The degree must have at least 1 credit.")
        Integer totalCredits,

        @NotBlank(message = "The university name must not be blank.")
        @Size(max = 100, message = "The university name must not exceed 100 characters.")
        String universityName
) {
}
