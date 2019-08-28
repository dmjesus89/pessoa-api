package br.com.pamcary.exception;

public class BadRequestAlertException extends RuntimeException {

	public BadRequestAlertException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
