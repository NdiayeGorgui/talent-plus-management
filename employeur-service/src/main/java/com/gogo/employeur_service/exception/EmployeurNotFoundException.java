package com.gogo.employeur_service.exception;

import java.io.Serial;

public class EmployeurNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public EmployeurNotFoundException(String string) {
		super(string);
		
	}
}