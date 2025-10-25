package ar.edu.unnoba.poo2025.torneos.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParticipantAlreadyExistsException.class)
    public ResponseEntity<Void> handleParticipantAlreadyExists(ParticipantAlreadyExistsException ex) {
        return ResponseEntity.status(ex.getCode()).build();
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<Void> handleParticipantNotFound(ParticipantNotFoundException ex) {
        return ResponseEntity.status(ex.getCode()).build();
    }

}