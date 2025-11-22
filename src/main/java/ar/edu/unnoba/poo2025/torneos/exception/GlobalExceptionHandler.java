package ar.edu.unnoba.poo2025.torneos.exception;

import ar.edu.unnoba.poo2025.torneos.dto.ExceptionResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	public GlobalExceptionHandler() {
	}

	@ExceptionHandler(AdminAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponseDTO> handleAdminAlreadyExists(AdminAlreadyExistsException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<ExceptionResponseDTO> handleAdminNotFound(AdminNotFoundException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(ParticipantAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponseDTO> handleParticipantAlreadyExists(ParticipantAlreadyExistsException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(ParticipantNotFoundException.class)
	public ResponseEntity<ExceptionResponseDTO> handleParticipantNotFound(ParticipantNotFoundException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(ParticipantAlredyInscribedInTournamentException.class)
	public ResponseEntity<ExceptionResponseDTO> handleParticipantAlreadyInscribed(ParticipantAlredyInscribedInTournamentException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<ExceptionResponseDTO> handleAuthenticationFailed(AuthenticationFailedException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(TournamentNotFoundException.class)
	public ResponseEntity<ExceptionResponseDTO> handleTournamentNotFound(TournamentNotFoundException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(CompetitionNotFoundException.class)
	public ResponseEntity<ExceptionResponseDTO> handleCompetitionNotFound(CompetitionNotFoundException ex) {
		return ResponseEntity.status(ex.getCode()).body(new ExceptionResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler(CompetitionFullException.class)
	public ResponseEntity<ExceptionResponseDTO> handleCompetitionFull(CompetitionFullException ex) {
		return ResponseEntity.status(409).body(new ExceptionResponseDTO(ex.getMessage()));
	}

}
