package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CreateParticipantRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CreateParticipantResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.service.AuthenticationService;
import ar.edu.unnoba.poo2025.torneos.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Participant", description = "Endpoints for participant authentication and account management")
public class ParticipantResource {

	private final ParticipantService participantService;
	private final ModelMapper modelMapper;
	private final AuthenticationService authenticationService;

	@Autowired
	public ParticipantResource(ParticipantService participantService, ModelMapper modelMapper, AuthenticationService authenticationService) {
		this.participantService = participantService;
		this.modelMapper = modelMapper;
		this.authenticationService = authenticationService;
	}

	@PostMapping(value = "/auth", produces = "application/json")
	@Operation(summary = "Authenticate participant",
	           description = "Allows a participant to authenticate and receive a JWT token.")
	public ResponseEntity<AuthenticationResponseDTO> authentication(@RequestBody AuthenticationRequestDTO dto) {
		Participant participant = modelMapper.map(dto, Participant.class);
		String token = authenticationService.authenticate(participant);
		AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(token);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@PostMapping(value = "/account", produces = "application/json")
	@Operation(summary = "Create participant account", description = "Creates a new participant account.")
	public ResponseEntity<CreateParticipantResponseDTO> create(@Valid @RequestBody CreateParticipantRequestDTO dto) {
		Participant participant = modelMapper.map(dto, Participant.class);
		participant = participantService.create(participant);
		CreateParticipantResponseDTO responseDTO = modelMapper.map(participant, CreateParticipantResponseDTO.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

}
