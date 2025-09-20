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
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(OffreNotFoundException.class)
    public ResponseEntity<?> handleOffreNotFound(OffreNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ProcessusNotFoundException.class)
    public ResponseEntity<?> handleProcessusNotFound(ProcessusNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<?> handleNotification(NotificationException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE));
    }


    //manipulation  exceptions globales

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Exception: {}",exception.getMessage());
        return  ResponseEntity.internalServerError().body(errorDetails);
    }
}
