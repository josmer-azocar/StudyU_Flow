package com.api.StudyU_Flow.domain.dto.response;

public record EvaluationResponseDto(
        Long idEvaluation,
        String evaluationName,
        Double grade,
        Double maxGrade,
        Double percentage,
        Long idRecord
) {
}
