package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.*;
import ar.edu.unnoba.poo2025.torneos.model.Admin;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import ar.edu.unnoba.poo2025.torneos.service.AdminService;
import ar.edu.unnoba.poo2025.torneos.service.AuthenticationService;
import ar.edu.unnoba.poo2025.torneos.service.TournamentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
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
	public ResponseEntity<AuthenticationResponseDTO> authentication(@RequestBody AuthenticationRequestDTO dto) {
		Admin admin = modelMapper.map(dto, Admin.class);
		String token = authenticationService.authenticate(admin);
		// en caso de lanzar una exception, el GlobalExceptionHandler la captura automaticamente.
		AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(token);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@PostMapping(value = "/accounts")
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
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		// Delegar eliminación a la capa de servicio
		adminService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/accounts")
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
	public ResponseEntity<List<TournamentResponseOrderDTO>> getTournaments() {
		// Obtener torneos ordenados por fecha DESC
		List<Tournament> tournaments = tournamentService.getTournamentsOrderDesc();
		// Mapear al DTO con fechas y publicado
		List<TournamentResponseOrderDTO> tournamentDTOs = tournaments.stream()
		                                                             .map(tournament -> modelMapper.map(tournament, TournamentResponseOrderDTO.class))
		                                                             .collect(Collectors.toList());

		return ResponseEntity.ok(tournamentDTOs);
	}

}
