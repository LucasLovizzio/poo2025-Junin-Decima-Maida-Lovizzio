package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ParticipantNotFoundException extends RuntimeException {

    private static final HttpStatusCode code = HttpStatus.NOT_FOUND;

    public ParticipantNotFoundException() {
        super();
    }

    public ParticipantNotFoundException(String message) {
        super(message);
    }

    public HttpStatusCode getCode() {
        return code;
    }
}
