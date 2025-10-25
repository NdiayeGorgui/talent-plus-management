package com.gogo.recrutement_service.exception;

import java.io.Serial;


public class ProcessusNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public ProcessusNotFoundException(String string) {
		super(string);
		
	}
}