package com.gogo.candidat_service.exception;

import java.io.Serial;


public class CandidatNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public CandidatNotFoundException(String string) {
		super(string);
		
	}
}