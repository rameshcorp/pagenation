package com.DATA.MIGRATION.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        // Return a 200 OK response with a custom error message
        return ResponseEntity.ok("Error: " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Return a 200 OK response for any runtime exceptions
        return ResponseEntity.ok("Internal Server Error: " + ex.getMessage());
    }

    // You can add more exception handlers as needed
}
