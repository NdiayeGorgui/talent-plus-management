package com.gogo.recrutement_service.exception;

import java.io.Serial;


public class RecruteurNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public RecruteurNotFoundException(String string) {
		super(string);
		
	}
}