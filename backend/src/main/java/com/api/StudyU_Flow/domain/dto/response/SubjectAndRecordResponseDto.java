package com.api.StudyU_Flow.domain.dto.response;


public record SubjectAndRecordResponseDto(

        SubjectResponseDto subjectData,

        StudentSubjectRecordResponseDto recordData
) {
}
