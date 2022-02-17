package pl.sofixit.interview.jsonGenerator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body("Request is not valid due to validation error: " + e.getMessage());
    }
}
