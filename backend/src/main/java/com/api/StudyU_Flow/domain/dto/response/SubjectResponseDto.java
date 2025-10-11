package com.api.StudyU_Flow.domain.dto.response;

public record SubjectResponseDto(

        Long idSubject,
        String name,
        Integer credits,
        Integer semester,
        Long idStudentDegree
) {
}
