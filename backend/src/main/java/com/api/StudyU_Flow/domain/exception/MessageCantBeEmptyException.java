package com.api.StudyU_Flow.domain.exception;

public class MessageCantBeEmptyException extends RuntimeException {
    public MessageCantBeEmptyException() {
        super("The message cannot be empty");
    }
}
