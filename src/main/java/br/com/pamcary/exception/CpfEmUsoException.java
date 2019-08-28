package br.com.pamcary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfEmUsoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CpfEmUsoException(String message) {
		super(message);
	}

	public CpfEmUsoException(String message, Throwable cause) {
		super(message, cause);
	}

}