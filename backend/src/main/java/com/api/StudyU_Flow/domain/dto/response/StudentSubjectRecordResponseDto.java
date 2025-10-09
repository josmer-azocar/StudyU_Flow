package com.api.StudyU_Flow.domain.dto.response;

import com.api.StudyU_Flow.domain.Enum.Status;

public record StudentSubjectRecordResponseDto(
        Long idRecord,
        Integer attempts,
        Double finalGrade,
        Status status,
        Long idStudent,
        Long idSubject
) {
}
