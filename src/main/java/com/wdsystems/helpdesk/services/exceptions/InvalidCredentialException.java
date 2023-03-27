package com.wdsystems.helpdesk.services.exceptions;

public class InvalidCredentialException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidCredentialException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCredentialException(String message) {
		super(message);
	}

	
}
