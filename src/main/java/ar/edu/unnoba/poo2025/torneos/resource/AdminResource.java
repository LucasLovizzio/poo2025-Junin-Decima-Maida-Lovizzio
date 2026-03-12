package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.*;
import ar.edu.unnoba.poo2025.torneos.model.Admin;
import ar.edu.unnoba.poo2025.torneos.model.Competition;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import ar.edu.unnoba.poo2025.torneos.security.CustomUserDetails;
import ar.edu.unnoba.poo2025.torneos.service.AdminService;
import ar.edu.unnoba.poo2025.torneos.service.AuthenticationService;
import ar.edu.unnoba.poo2025.torneos.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Endpoints for admin operations")
public class AdminResource {

	private final ModelMapper modelMapper;
	private final AuthenticationService authenticationService;
	private final AdminService adminService;
	private final TournamentService tournamentService;

	@Autowired
	public AdminResource(ModelMapper modelMapper, AuthenticationService authenticationService, AdminService adminService, TournamentService tournamentService) {
		this.modelMapper = modelMapper;
		this.authenticationService = authenticationService;
		this.adminService = adminService;
		this.tournamentService = tournamentService;
	}

	@PostMapping(value = "/auth", produces = "application/json")
	@Operation(summary = "Authenticate an admin and return a JWT token")
	public ResponseEntity<AuthenticationResponseDTO> authentication(@RequestBody AuthenticationRequestDTO dto) {
		Admin admin = modelMapper.map(dto, Admin.class);
		String token = authenticationService.authenticate(admin);
		// en caso de lanzar una exception, el GlobalExceptionHandler la captura automaticamente.
		AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(token);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@PostMapping(value = "/accounts")
	@Operation(summary = "Create a new admin account")
	public ResponseEntity<CreateAdminResponseDTO> create(@RequestBody CreateAdminRequestDTO dto) {
		// Convertir DTO → entidad
		Admin admin = modelMapper.map(dto, Admin.class);

		// Delegar creación a la capa de servicio
		admin = adminService.create(admin);
		CreateAdminResponseDTO responseDTO = modelMapper.map(admin, CreateAdminResponseDTO.class);

		// si esta bien retorna 201
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@DeleteMapping("/accounts/{id}")
	@Operation(summary = "Delete an admin account by ID", description = "Cannot delete own account")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {
		Long currentAdminId = userDetails.getUser().getId();

		adminService.delete(id, currentAdminId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/accounts")
	@Operation(summary = "Get all admin users", description = "Returns a list of all admin users")
	public ResponseEntity<List<AdminResponseDTO>> getUsersAdmin() {

		// Obtiene los admins
		List<Admin> admins = adminService.getUsersAdmin();

		// Mapea entidades a DTOs
		List<AdminResponseDTO> adminsDTOs = admins.stream()
		                                          .map(admin -> modelMapper.map(admin, AdminResponseDTO.class))
		                                          .collect(Collectors.toList());

		// Devuelve la lista con 200 OK
		return ResponseEntity.ok(adminsDTOs);
	}

	@GetMapping("/tournaments")
	@Operation(summary = "Get all tournaments ordered by date descending",
	           description = "Returns a list of all tournaments ordered by date in descending order")
	public ResponseEntity<List<TournamentResponseDTO>> getTournaments() {
		// Obtener torneos ordenados por fecha DESC
		List<Tournament> tournaments = tournamentService.getTournamentsOrderDesc();
		// Mapear al DTO completo con toda la información
		List<TournamentResponseDTO> tournamentDTOs = tournaments.stream()
		                                                             .map(tournament -> modelMapper.map(tournament, TournamentResponseDTO.class))
		                                                             .collect(Collectors.toList());

		return ResponseEntity.ok(tournamentDTOs);
	}

	@GetMapping("/tournaments/{tournamentId}/competitions")
	@Operation(summary = "Get all competitions for a tournament",
	           description = "Returns all competitions within a specific tournament for admin management")
	public ResponseEntity<List<CompetitionResponseDTO>> getCompetitionsByTournament(@PathVariable Long tournamentId) {
		List<CompetitionResponseDTO> competitions = tournamentService.getAdminCompetitionsByTournamentId(tournamentId).stream()
		                                                             .map(comp -> modelMapper.map(comp, CompetitionResponseDTO.class))
		                                                             .collect(Collectors.toList());
		return ResponseEntity.ok(competitions);
	}

	@GetMapping("/tournaments/{tournamentId}/competitions/{competitionId}")
	@Operation(summary = "Get competition details by tournament ID and competition ID",
	           description = "Returns the details of a specific competition within a specific tournament")
	public ResponseEntity<CompetitionResponseDTO> getTournamentCompetition(@PathVariable Long tournamentId, @PathVariable Long competitionId) {

		Competition competition = tournamentService.getCompetitionByIdAndTournamentIdAdmin(competitionId, tournamentId);
		CompetitionResponseDTO response = modelMapper.map(competition, CompetitionResponseDTO.class);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/tournaments")
	@Operation(summary = "Create a new tournament",
	           description = "Creates a new tournament with the provided details")
	public ResponseEntity<TournamentResponseDTO> createTournament(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CreateTournamentRequestDTO tournamentRequest) {
		Admin admin = (Admin) userDetails.getUser();
		Tournament tournament = modelMapper.map(tournamentRequest, Tournament.class);
		tournament.setAdmin(admin);
		tournament.setPublished(false);

		Tournament createdTournament = tournamentService.createTournament(tournament, admin);
		TournamentResponseDTO response = modelMapper.map(createdTournament, TournamentResponseDTO.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/tournaments/{tournamentId}")
	@Operation(summary = "Create a new competition within a tournament",
	           description = "Creates a new competition within the specified tournament")
	public ResponseEntity<CompetitionResponseDTO> createCompetition(@PathVariable Long tournamentId, @RequestBody CompetitionRequestDTO competitionRequest) {
		Competition competition = tournamentService.createCompetition(tournamentId, competitionRequest);
		CompetitionResponseDTO response = modelMapper.map(competition, CompetitionResponseDTO.class);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/tournaments/{tournamentId}")
	@Operation(summary = "Change competition details within a tournament",
	           description = "Updates the details of a specific competition within the specified tournament")
	public ResponseEntity<CompetitionResponseDTO> changeTournamentCompetitionDetails(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long tournamentId,
		@RequestBody CompetitionRequestDTO competitionRequest) {

		Admin admin = (Admin) userDetails.getUser();

		Competition competition = tournamentService.changeTournamentCompetitionDetails(
			tournamentId,
			competitionRequest,
			admin);

		CompetitionResponseDTO response = modelMapper.map(competition, CompetitionResponseDTO.class);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/tournaments/{tournamentId}/competitions/{competitionId}")
	@Operation(summary = "Remove a competition from a tournament",
	           description = "Removes the specified competition from the specified tournament")
	public ResponseEntity<Void> removeCompetition(@PathVariable Long tournamentId, @PathVariable Long competitionId) {
		tournamentService.removeCompetition(tournamentId, competitionId);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PatchMapping("/tournaments/{tournamentId}/published")
	@Operation(summary = "Publish a tournament",
	           description = "Sets the tournament's published status to true")
	public ResponseEntity<TournamentResponseDTO> publishTournament(@PathVariable Long tournamentId) {
		Tournament tournament = tournamentService.publish(tournamentId);
		TournamentResponseDTO response = tournamentService.convertToDto(tournament);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tournaments/{tournamentId}/competitions/{competitionId}/inscripciones")
	@Operation(summary = "Get inscriptions for a competition within a tournament",
	           description = "Returns a list of inscriptions for the specified competition within the specified tournament")
	public ResponseEntity<List<InscriptionResponseDTO>> getInscriptions(@PathVariable Long tournamentId, @PathVariable Long competitionId) {

		Competition competition = tournamentService.getCompetitionByIdAndTournamentId(competitionId, tournamentId);
		List<InscriptionResponseDTO> response = competition.getInscriptions().stream()
		                                                   .map(inscription -> modelMapper.map(inscription, InscriptionResponseDTO.class))
		                                                   .collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

}
