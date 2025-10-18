package com.api.StudyU_Flow.domain.dto.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateStudentDegreeDto(
        @Min(value = 1, message = "The degree must have at least 1 semester.")
        Integer quantitySemesters,

        @Min(value = 1, message = "The degree must have at least 1 credit.")
        Integer totalCredits,

        @Size(max = 100, message = "The university name must not exceed 100 characters.")
        String universityName
) {
}
