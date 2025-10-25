package com.gogo.candidat_service.exception;

import java.io.Serial;


public class CvNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public CvNotFoundException(String string) {
		super(string);
		
	}
}