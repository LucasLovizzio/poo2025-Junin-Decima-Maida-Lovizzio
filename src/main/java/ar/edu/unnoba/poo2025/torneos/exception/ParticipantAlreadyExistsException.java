package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ParticipantAlreadyExistsException extends RuntimeException {

    private static final HttpStatusCode code = HttpStatus.CONFLICT;

    public ParticipantAlreadyExistsException() {
        super();
    }

    public ParticipantAlreadyExistsException(String message) {
        super(message);
    }

    public HttpStatusCode getCode() {
        return code;
    }
}
