package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class CompetitionFullException extends RuntimeException {

	private static final HttpStatusCode CODE = HttpStatus.CONFLICT;

	public CompetitionFullException(String message) {
		super(message);
	}

	public HttpStatusCode getCode() {
		return CODE;
	}

}
