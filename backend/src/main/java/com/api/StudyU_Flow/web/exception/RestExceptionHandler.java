package com.api.StudyU_Flow.web.exception;

import com.api.StudyU_Flow.domain.exception.DegreeDoesNotExistsException;
import com.api.StudyU_Flow.domain.exception.StudentAlreadyExistsException;
import com.api.StudyU_Flow.domain.exception.StudentDoesNotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<Error> handleException(StudentAlreadyExistsException ex){
        Error error = new Error("username-already-exists", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(StudentDoesNotExistsException.class)
    public ResponseEntity<Error> handleException(StudentDoesNotExistsException ex){
        Error error = new Error("username-does-not-exist", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DegreeDoesNotExistsException.class)
    public ResponseEntity<Error> handleException(DegreeDoesNotExistsException ex){
        Error error = new Error("student-degree-does-not-exist", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleException(MethodArgumentNotValidException ex){
        List<Error> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(new Error(error.getField(), error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex){
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
}
