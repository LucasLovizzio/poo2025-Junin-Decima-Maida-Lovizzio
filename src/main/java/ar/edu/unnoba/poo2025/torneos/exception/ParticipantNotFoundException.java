package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatusCode;

public class ParticipantNotFoundException extends RuntimeException {

    private final HttpStatusCode code = HttpStatusCode.valueOf(404);

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
