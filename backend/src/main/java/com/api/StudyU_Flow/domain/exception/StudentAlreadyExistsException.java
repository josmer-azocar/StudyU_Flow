package com.api.StudyU_Flow.domain.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String username) {
        super("the username: " + username + " already exists");
    }
}
