package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.InscriptionDetailResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscriptionResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.Inscription;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import ar.edu.unnoba.poo2025.torneos.security.CustomUserDetails;
import ar.edu.unnoba.poo2025.torneos.service.InscriptionService;
import ar.edu.unnoba.poo2025.torneos.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inscriptions")
@Tag(name = "Inscriptions", description = "Endpoints for participant inscriptions")
public class InscriptionResource {

	private final InscriptionService inscriptionService;
	private final TournamentService tournamentService;
	private final ModelMapper modelMapper;

	@Autowired
	public InscriptionResource(InscriptionService inscriptionService, TournamentService tournamentService, ModelMapper modelMapper) {
		this.inscriptionService = inscriptionService;
        this.tournamentService = tournamentService;
        this.modelMapper = modelMapper;
	}

	@GetMapping
	@Operation(summary = "Get all inscriptions for the authenticated participant",
	           description = "Returns a list of all inscriptions associated with the authenticated participant.")
	public ResponseEntity<List<InscriptionResponseDTO>> getParticipantInscriptions(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		Participant participant = (Participant) userDetails.getUser();

		List<InscriptionResponseDTO> inscriptions = inscriptionService
			.getInscriptionsByParticipantId(participant.getId())
			.stream()
			.map(inscription -> new InscriptionResponseDTO(
				inscription.getId(),
				inscription.getInscriptionDate(),
				inscription.getFinalPrice(),
				inscription.getCompetition().getTournament().getId(),
				inscription.getCompetition().getTournament().getName(),
				inscription.getCompetition().getId(),
				inscription.getCompetition().getName()
			))
			.collect(Collectors.toList());

		return ResponseEntity.ok(inscriptions);
	}

	@GetMapping("/tournament/{tournamentId}/check")
	@Operation(summary = "Check if the authenticated participant has an inscription in a tournament")
	public ResponseEntity<Map<String, Boolean>> hasInscriptionInTournament(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long tournamentId) {

		Participant participant = (Participant) userDetails.getUser();
		boolean hasInscription = inscriptionService.hasInscriptionInTournament(participant.getId(), tournamentId);
		return ResponseEntity.ok(Map.of("hasInscription", hasInscription));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get inscription detail by ID for the authenticated participant",
	           description = "Returns detailed information about a specific inscription belonging to the authenticated participant.")
	public ResponseEntity<InscriptionDetailResponseDTO> getParticipantInscriptionDetail(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long id) {

		Participant participant = (Participant) userDetails.getUser();

		Inscription inscription = inscriptionService.getInscriptionByIdAndParticipantId(id, participant.getId());

		InscriptionDetailResponseDTO dto = modelMapper.map(
				inscription,
				InscriptionDetailResponseDTO.class
		);

		Tournament t = tournamentService.getTournamentById(inscription.getCompetition().getTournament().getId());

		dto.setTournamentId(t.getId());
		dto.setTournamentName(t.getName());
		dto.setTournamentDescription(t.getDescription());
		dto.setTournamentStartDate(t.getStartDate());
		dto.setTournamentFinishDate(t.getEndDate());

		return ResponseEntity.ok(dto);
	}

}
