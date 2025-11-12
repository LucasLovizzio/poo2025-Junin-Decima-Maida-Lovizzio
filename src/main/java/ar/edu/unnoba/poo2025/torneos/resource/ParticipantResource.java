package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CreateParticipantRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CreateParticipantResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.service.AuthenticationService;
import ar.edu.unnoba.poo2025.torneos.service.ParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participants")
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

    @PostMapping("/")
    public ResponseEntity<CreateParticipantResponseDTO> create(@RequestBody CreateParticipantRequestDTO dto) {
        // Convertir DTO → entidad
        Participant participant = modelMapper.map(dto, Participant.class);

        // Delegar creación a la capa de servicio
        participant = participantService.create(participant);
        CreateParticipantResponseDTO responseDTO = modelMapper.map(participant, CreateParticipantResponseDTO.class);

        // si esta bien retorna 201
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<AuthenticationResponseDTO> authentication(@RequestBody AuthenticationRequestDTO dto) {
        Participant participant = modelMapper.map(dto, Participant.class);
        String token = authenticationService.authenticate(participant);
        // en caso de lanzar una exception, el GlobalExceptionHandler la captura automaticamente.
        AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(token);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
