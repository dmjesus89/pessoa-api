package br.com.pamcary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NameAlreadyUsedException extends RuntimeException {

	public NameAlreadyUsedException(String message) {
		super(message);
	}

	public NameAlreadyUsedException(String message, Throwable cause) {
		super(message, cause);
	}

}