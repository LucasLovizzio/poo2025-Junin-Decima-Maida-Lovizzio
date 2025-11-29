package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AuthorizationFailedException extends RuntimeException {

    private static final HttpStatusCode CODE = HttpStatus.FORBIDDEN;

    public AuthorizationFailedException() {
        super("Authorization failed: Invalid or expired token");
    }

    public AuthorizationFailedException(String message) {
        super(message);
    }

    public HttpStatusCode getCode() {
        return CODE;
    }
}