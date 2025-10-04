package com.api.StudyU_Flow.domain.dto.response;

import com.api.StudyU_Flow.domain.Enum.Gender;

import java.time.LocalDate;

public record StudentResponseDto(
         Long idStudent,
         String username,
         String firstName,
         String lastName,
         LocalDate dateOfBirth,
         Gender gender
) {
}
