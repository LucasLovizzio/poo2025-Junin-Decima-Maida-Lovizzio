package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public class ParticipantAlreadyExistsException extends RuntimeException {

	private static final HttpStatusCode CODE = HttpStatus.CONFLICT;
	private List <String> errors;

	public ParticipantAlreadyExistsException() {
		super();
	}

	public ParticipantAlreadyExistsException(String message) {
		super(message);
	}

	public ParticipantAlreadyExistsException(List <String> errors) {
		super(String.join(System.lineSeparator(), errors));
		this.errors = errors;
	}

	public HttpStatusCode getCode() {
		return CODE;
	}

}
