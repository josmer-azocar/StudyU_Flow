package com.api.StudyU_Flow.domain.exception;

public class EvaluationDoesNotExistsException extends RuntimeException {
    public EvaluationDoesNotExistsException(Long idEvaluation) {
        super("the evaluation with ID: " + idEvaluation + " does not exist");
    }
}
