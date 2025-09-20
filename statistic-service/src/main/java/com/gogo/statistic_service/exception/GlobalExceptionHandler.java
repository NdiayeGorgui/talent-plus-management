package com.gogo.statistic_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ----- Exceptions spécifiques -----

    @ExceptionHandler(CandidatNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCandidatNotFound(CandidatNotFoundException ex) {
        log.error("CandidatNotFoundException: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OffreNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleOffreNotFound(OffreNotFoundException ex) {
        log.error("OffreNotFoundException: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecruteurNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRecruteurNotFound(RecruteurNotFoundException ex) {
        log.error("RecruteurNotFoundException: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StatServiceException.class)
    public ResponseEntity<ErrorDetails> handleStatServiceException(StatServiceException ex) {
        log.error("StatServiceException: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProcessusNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProcessusNotFound(ProcessusNotFoundException ex) {
        log.error("ProcessusNotFoundException: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // ----- Exceptions globales -----
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
