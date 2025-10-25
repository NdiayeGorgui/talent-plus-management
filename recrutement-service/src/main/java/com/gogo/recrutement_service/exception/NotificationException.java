package com.gogo.recrutement_service.exception;

import java.io.Serial;


public class NotificationException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public NotificationException(String string) {
		super(string);
		
	}
}