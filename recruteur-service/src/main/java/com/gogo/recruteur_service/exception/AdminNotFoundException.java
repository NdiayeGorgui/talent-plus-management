package com.gogo.recruteur_service.exception;

import java.io.Serial;

public class AdminNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public AdminNotFoundException(String string) {
		super(string);
		
	}
}