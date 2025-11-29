package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class CompetitionNotFoundException extends RuntimeException {

	private static final HttpStatusCode CODE = HttpStatus.NOT_FOUND;

	public CompetitionNotFoundException() {
	}

	public CompetitionNotFoundException(String message) {
		super(message);
	}

	public HttpStatusCode getCode() {
		return CODE;
	}

}
