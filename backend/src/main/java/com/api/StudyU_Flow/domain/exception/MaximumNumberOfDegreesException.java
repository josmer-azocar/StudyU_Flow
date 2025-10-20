package com.api.StudyU_Flow.domain.exception;

public class MaximumNumberOfDegreesException extends RuntimeException {
    public MaximumNumberOfDegreesException(String username) {
        super("the user: " + username + " has reached the maximum number of degrees");
    }
}
