package pl.sofixit.interview.csvConverter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body("Request is not valid due to validation error: " + e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body("Request is not valid due to validation error: " + e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleIO(IOException e){
        return ResponseEntity.internalServerError().body("Cannot download csv file: " + e.getMessage());
    }
}
