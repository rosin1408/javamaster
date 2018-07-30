package br.com.biblioteca.exception;

public class ValidationException extends Exception {
	
	private static final long serialVersionUID = -8743392230607326137L;
	
	public ValidationException(String message) {
		super(message);
	}
}
