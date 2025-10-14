package com.api.StudyU_Flow.domain.exception;

public class DegreeDoesNotExistsException extends RuntimeException {
    public DegreeDoesNotExistsException(Long idDegree) {
        super("the degree with ID: " + idDegree + " does not exist");
    }
}
