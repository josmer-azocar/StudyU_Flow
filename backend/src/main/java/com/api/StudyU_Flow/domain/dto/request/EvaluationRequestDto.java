package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record EvaluationRequestDto(

        @NotBlank(message = "Evaluation name cannot be empty.")
        @Size(max = 100, message = "Evaluation name cannot exceed 100 characters.")
        String evaluationName,

        @NotNull(message = "Grade is required.")
        @DecimalMin(value = "0.0", message = "Grade cannot be negative.")
        Double grade,

        @NotNull(message = "Maximum grade is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Maximum grade must be greater than 0.")
        Double maxGrade,

        @NotNull(message = "Percentage weight is required.")
        @DecimalMin(value = "0.0", message = "Percentage cannot be negative.")
        @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100.")
        Double percentage,

        @NotNull(message = "Record ID is required.")
        @Min(value = 1, message = "Record ID is not valid.")
        Long idRecord
) {
}
