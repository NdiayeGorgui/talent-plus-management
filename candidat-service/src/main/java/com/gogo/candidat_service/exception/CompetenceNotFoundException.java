package com.gogo.candidat_service.exception;

import java.io.Serial;


public class CompetenceNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public CompetenceNotFoundException(String string) {
		super(string);
		
	}
}