package ar.edu.unnoba.poo2025.torneos.exception;

import ar.edu.unnoba.poo2025.torneos.dto.ExceptionResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParticipantAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleParticipantAlreadyExists(ParticipantAlreadyExistsException ex) {
        return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleParticipantNotFound(ParticipantNotFoundException ex) {
        return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
    }

}