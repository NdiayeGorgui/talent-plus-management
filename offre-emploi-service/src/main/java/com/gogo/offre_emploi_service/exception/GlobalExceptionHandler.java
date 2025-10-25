package com.gogo.offre_emploi_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 📌 Exception spécifique pour une offre introuvable
    @ExceptionHandler(OffreNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleOffreNotFoundException(OffreNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
        log.error("Exception: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(errorDetails);
    }

    // 📌 Gestion des exceptions globales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        log.error("Unexpected exception: {}", exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
