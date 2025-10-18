package com.api.StudyU_Flow.domain.dto.response;

public record DegreeCurrentProgressDto(
        Double percentage,
        Integer approvedCredits,
        Integer approvedSubjects,
        Integer currentSemester,
        Integer missingCredits
      //  Integer missingSubjects
) {
}
