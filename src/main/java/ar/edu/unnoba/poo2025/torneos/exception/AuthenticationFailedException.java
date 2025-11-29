package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AuthenticationFailedException extends RuntimeException {

    private static final HttpStatusCode CODE = HttpStatus.UNAUTHORIZED;

    public AuthenticationFailedException() {
        super("Authentication failed: Invalid credentials");
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public HttpStatusCode getCode() {
        return CODE;
    }
}