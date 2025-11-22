package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ParticipantAlredyInscribedInTournamentException extends RuntimeException {

	public static final HttpStatus CODE = HttpStatus.CONFLICT;

	public ParticipantAlredyInscribedInTournamentException(String message) {
		super(message);
	}

	public HttpStatusCode getCode() {
		return CODE;
	}


}
