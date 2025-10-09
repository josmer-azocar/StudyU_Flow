package com.api.StudyU_Flow.domain.dto.response;

public record StudentDegreeResponseDto(
        Long idStudentDegree,
        Long idDegree,
        Long idStudent,
        Integer quantitySemesters,
        Integer totalCredits,
        String universityName
) {
}
