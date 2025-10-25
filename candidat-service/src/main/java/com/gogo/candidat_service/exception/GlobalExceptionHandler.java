package com.gogo.candidat_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //manipulation exceptions spécifiques
    @ExceptionHandler(CandidatNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(CandidatNotFoundException exception){
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


    @ExceptionHandler(CompetenceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(CompetenceNotFoundException exception){
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

    @ExceptionHandler(CompetenceLinguistiqueNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(CompetenceLinguistiqueNotFoundException exception){
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

    @ExceptionHandler(ExperienceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ExperienceNotFoundException exception){
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

    @ExceptionHandler(LettreMotivationNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(LettreMotivationNotFoundException exception){
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

    @ExceptionHandler(CvNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(CvNotFoundException exception){
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

    @ExceptionHandler(MetaDonneeRHNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(MetaDonneeRHNotFoundException exception){
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

    //manipulation  exceptions globales

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Exception: {}",exception.getMessage());
        return  ResponseEntity.internalServerError().body(errorDetails);
    }
}
