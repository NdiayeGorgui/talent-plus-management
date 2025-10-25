package com.gogo.candidat_service.exception;

import java.io.Serial;


public class LettreMotivationNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public LettreMotivationNotFoundException(String string) {
		super(string);
		
	}
}