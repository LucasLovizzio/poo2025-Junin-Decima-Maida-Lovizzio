package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.CreateParticipantRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CreateParticipantResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
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

    private ParticipantService participantService;
    private ModelMapper modelMapper;

    @Autowired
    public ParticipantResource(ParticipantService participantService, ModelMapper modelMapper) {
        this.participantService = participantService;
        this.modelMapper = modelMapper;
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
}

