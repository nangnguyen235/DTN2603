package com.dtn2603.buoi2.exception;


import com.dtn2603.buoi2.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> globalIllegalArgumentExceptionHandler(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setPath(request.getRequestURI());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setPath(request.getRequestURI());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
