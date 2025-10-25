package com.gogo.candidat_service.exception;

import java.io.Serial;



public class CompetenceLinguistiqueNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public CompetenceLinguistiqueNotFoundException(String string) {
        super(string);

    }
}