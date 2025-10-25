package com.gogo.offre_emploi_service.exception;

import java.io.Serial;

public class OffreNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public OffreNotFoundException(String string) {
		super(string);
		
	}
}