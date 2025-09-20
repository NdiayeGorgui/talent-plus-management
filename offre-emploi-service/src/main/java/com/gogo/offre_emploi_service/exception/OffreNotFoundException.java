package com.gogo.offre_emploi_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OffreNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public OffreNotFoundException(String string) {
		super(string);
		
	}
}