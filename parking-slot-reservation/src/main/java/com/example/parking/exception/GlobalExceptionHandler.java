//package com.example.parking.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Handle validation errors like @NotBlank, @Email, etc.
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
//        Map<String, Object> response = new HashMap<>();
//        Map<String, String> errors = new HashMap<>();
//
//        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.put(error.getField(), error.getDefaultMessage());
//        }
//
//        response.put("status", HttpStatus.BAD_REQUEST.value());
//        response.put("message", "Validation failed");
//        response.put("errors", errors);
//        response.put("timestamp", LocalDateTime.now());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    // Handle resource not found exceptions
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", HttpStatus.NOT_FOUND.value());
//        response.put("message", ex.getMessage());
//        response.put("timestamp", LocalDateTime.now());
//
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    // Handle all other unhandled exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.put("message", ex.getMessage());
//        response.put("timestamp", LocalDateTime.now());
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
