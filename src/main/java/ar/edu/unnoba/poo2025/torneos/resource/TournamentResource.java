package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.CompetitionResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TournamentResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import ar.edu.unnoba.poo2025.torneos.security.CustomUserDetails;
import ar.edu.unnoba.poo2025.torneos.service.TournamentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TournamentResource {

	private final TournamentService tournamentService;
	private final ModelMapper modelMapper;

	@Autowired
	public TournamentResource(TournamentService tournamentService, ModelMapper modelMapper) {
		this.tournamentService = tournamentService;
		this.modelMapper = modelMapper;
	}

	// Retorna el listado de torneos publicados próximos a realizarse o en desarrollo
	@GetMapping("/tournaments")
	public ResponseEntity<List<TournamentResponseDTO>> getPublishedAndNextTournamentsOrInProgress(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		List<TournamentResponseDTO> tournamentDTOs = tournamentService.getPublishedAndNextTournamentsOrInProgress().stream()
		                                                              .map(tournament -> modelMapper.map(tournament, TournamentResponseDTO.class))
		                                                              .collect(Collectors.toList());

		return ResponseEntity.ok(tournamentDTOs);
	}

	@GetMapping("/tournaments/{id}")
	public ResponseEntity<TournamentResponseDTO> getTournamentById(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long id) {

		Tournament tournament = tournamentService.getTournamentById(id);
		TournamentResponseDTO tournamentDTO = modelMapper.map(tournament, TournamentResponseDTO.class);
		return ResponseEntity.ok(tournamentDTO);
	}

	@GetMapping("/tournaments/{tournamentId}/competitions")
	public ResponseEntity<List<CompetitionResponseDTO>> getCompetitionsByTournamentId(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long tournamentId) {

		List<CompetitionResponseDTO> competitions = tournamentService.getCompetitionsByTournamentId(tournamentId).stream()
		                                                             .map(competition -> modelMapper.map(competition, CompetitionResponseDTO.class))
		                                                             .collect(Collectors.toList());

		return ResponseEntity.ok(competitions);
	}

	@GetMapping("/tournaments/{tournamentId}/competitions/{id}")
	public ResponseEntity<CompetitionResponseDTO> getCompetitionById(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long tournamentId,
		@PathVariable Long id) {

		CompetitionResponseDTO competition = modelMapper.map(
			tournamentService.getCompetitionByIdAndTournamentId(id, tournamentId),
			CompetitionResponseDTO.class
		);

		return ResponseEntity.ok(competition);
	}

	@PostMapping("/tournaments/{tournamentId}/competitions/{id}/inscription")
	public ResponseEntity<Void> inscribeParticipantInCompetition(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long tournamentId,
		@PathVariable Long id) {

		Participant participant = (Participant) userDetails.getUser();

		tournamentService.inscribeParticipantInCompetition(participant, tournamentId, id);

		return ResponseEntity.noContent().build();
	}

}
