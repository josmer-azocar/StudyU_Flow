package com.api.StudyU_Flow.domain.exception;

public class SubjectRecordDoesNotExistsException extends RuntimeException {
    public SubjectRecordDoesNotExistsException(Long idRecord) {
        super("the subject record with ID: " + idRecord + " does not exist");
    }
}
