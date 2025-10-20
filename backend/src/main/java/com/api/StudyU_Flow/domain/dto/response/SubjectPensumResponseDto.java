package com.api.StudyU_Flow.domain.dto.response;

public record SubjectPensumResponseDto(
        String name,
        Integer credits,
        Integer semester,
        Long idStudentDegree
) {
}
