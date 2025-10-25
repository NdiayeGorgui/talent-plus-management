package com.gogo.recrutement_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CandidatNotFoundException.class)
    public ResponseEntity<?> handleCandidatNotFound(CandidatNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(errorDetails);
    }

    @ExceptionHandler(OffreNotFoundException.class)
    public ResponseEntity<?> handleOffreNotFound(OffreNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(errorDetails);
    }

    @ExceptionHandler(ProcessusNotFoundException.class)
    public ResponseEntity<?> handleProcessusNotFound(ProcessusNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(errorDetails);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<?> handleNotification(NotificationException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(errorDetails);
    }


    //manipulation  exceptions globales

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Exception: {}",exception.getMessage());
        return  ResponseEntity.internalServerError().body(errorDetails);
    }
}
