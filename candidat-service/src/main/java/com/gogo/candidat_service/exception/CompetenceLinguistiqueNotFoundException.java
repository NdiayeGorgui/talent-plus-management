package com.gogo.candidat_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompetenceLinguistiqueNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public CompetenceLinguistiqueNotFoundException(String string) {
        super(string);

    }
}