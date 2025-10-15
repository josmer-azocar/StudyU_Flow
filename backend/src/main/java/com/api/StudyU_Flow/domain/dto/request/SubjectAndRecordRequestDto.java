package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubjectAndRecordRequestDto(

        @NotNull
        SubjectRequestDto subjectData,

        @NotNull
        StudentSubjectRecordRequestDto recordData
) {
}
