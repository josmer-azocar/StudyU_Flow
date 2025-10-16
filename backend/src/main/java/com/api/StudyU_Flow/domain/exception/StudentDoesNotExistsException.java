package com.api.StudyU_Flow.domain.exception;

public class StudentDoesNotExistsException extends RuntimeException {
    public StudentDoesNotExistsException(String username) {
        super("the username: " + username + " does not exist");
    }

    public StudentDoesNotExistsException(Long idStudent) {
        super("the user with ID: " + idStudent + " does not exist");
    }
}
