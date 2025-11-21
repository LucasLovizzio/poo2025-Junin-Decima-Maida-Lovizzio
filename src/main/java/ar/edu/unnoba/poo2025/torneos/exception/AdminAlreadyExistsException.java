package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AdminAlreadyExistsException extends RuntimeException {

    private static final HttpStatusCode code = HttpStatus.CONFLICT;

    public AdminAlreadyExistsException() {
        super();
    }

    public AdminAlreadyExistsException(String message) {
        super(message);
    }

    public HttpStatusCode getCode() {
        return code;
    }

}
