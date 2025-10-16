package com.api.StudyU_Flow.domain.exception;

public class SubjectDoesNotExistsException extends RuntimeException {
    public SubjectDoesNotExistsException(Long idSubject) {
        super("the subject with ID: " + idSubject + " does not exist or does not match with the user");
    }
}
