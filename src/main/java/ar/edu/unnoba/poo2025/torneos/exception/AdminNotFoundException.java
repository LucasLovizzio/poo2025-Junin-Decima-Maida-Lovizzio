package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AdminNotFoundException extends RuntimeException {

    private static final HttpStatusCode code = HttpStatus.NOT_FOUND;

    public AdminNotFoundException() {
        super();
    }

    public AdminNotFoundException(String message) {
        super(message);
    }

    public HttpStatusCode getCode() {
        return code;
    }

}
