package com.springframework.msscbeerservice.web.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;


import java.util.HashMap;

@RequiredArgsConstructor
@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HashMap<String, HashMap<String, String>>> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        HashMap<String, String> errors = new HashMap<>();
        HashMap<String, HashMap<String, String>> exception = new HashMap<>();
        constraintViolationException.getConstraintViolations().forEach(violation -> {
            errors.put("field:", violation.getPropertyPath().toString());
            errors.put("message:", violation.getMessage());
        });
        exception.put("fieldErrors:", errors);
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, HashMap<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();
        HashMap<String, HashMap<String, String>> exception = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put("field:", fieldError.getField());
            errors.put("message:", fieldError.getDefaultMessage());
        });
        exception.put("fieldErrors:", errors);
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
