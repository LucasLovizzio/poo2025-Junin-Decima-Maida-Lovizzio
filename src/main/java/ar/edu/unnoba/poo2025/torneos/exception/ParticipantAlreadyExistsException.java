package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatusCode;

public class ParticipantAlreadyExistsException extends RuntimeException {

    private final HttpStatusCode code = HttpStatusCode.valueOf(409);

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
