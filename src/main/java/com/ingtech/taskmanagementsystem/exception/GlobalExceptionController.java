package com.ingtech.taskmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFoundException(TaskNotFoundException ex) {

        log.error("Task not found: {}", ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDate.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {

        log.error("ResponseStatusException: status={}, reason={},", ex.getStatusCode(), ex.getReason(), ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", ex.getStatusCode().value());
        body.put("error", ((HttpStatus) ex.getStatusCode()).getReasonPhrase());
        body.put("message", ex.getReason());
        body.put("timestamp", LocalDate.now());

        return ResponseEntity.status(ex.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }


}
