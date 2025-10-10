package com.api.StudyU_Flow.domain.dto.update;

import com.api.StudyU_Flow.domain.Enum.Gender;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

public record UpdateStudentDto (

        @NotNull(message = "Date of birth is required.")
        LocalDate dateOfBirth,

        @NotNull
        Gender gender
){
}
