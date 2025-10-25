package com.gogo.candidat_service.exception;

import java.io.Serial;

public class MetaDonneeRHNotFoundException extends Exception{


    @Serial
    private static final long serialVersionUID = 1L;

    public MetaDonneeRHNotFoundException(String string) {
        super(string);

    }
}
