package com.gogo.candidat_service.exception;

import java.io.Serial;


public class ExperienceNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public ExperienceNotFoundException(String string) {
		super(string);
		
	}
}