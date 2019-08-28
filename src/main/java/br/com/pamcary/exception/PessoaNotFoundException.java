package br.com.pamcary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PessoaNotFoundException extends RuntimeException {

	public PessoaNotFoundException(String message) {
		super(message);
	}
}
