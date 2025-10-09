package com.api.StudyU_Flow.domain.dto.request;

import com.api.StudyU_Flow.domain.Enum.Status;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record StudentSubjectRecordRequestDto(

        @NotNull(message = "The number of attempts is required.")
        @Min(value = 0, message = "Attempts must be at least 0.")
        Integer attempts,

        @DecimalMin(value = "0.0", message = "Final grade cannot be negative.")
        Double finalGrade,

        @NotNull(message = "Status cannot be empty.")
        Status status,

        @NotNull(message = "Student ID is required.")
        @Min(value = 1, message = "Student ID is not valid.")
        Long idStudent,

        @NotNull(message = "Subject ID is required.")
        @Min(value = 1, message = "Subject ID is not valid.")
        Long idSubject
) {
}
