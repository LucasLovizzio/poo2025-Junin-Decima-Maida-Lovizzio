package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.CreateParticipantRequestDTO;
import ar.edu.unnoba.poo2025.torneos.model.Participante;
import ar.edu.unnoba.poo2025.torneos.service.ParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participant")

public class ParticipantResource {

        private ParticipantService participantService;
        private ModelMapper modelMapper;

    public ParticipantResource(ParticipantService participantService, ModelMapper modelMapper) {
        this.participantService = participantService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateParticipantRequestDTO dto) {
        // Convertir DTO → entidad
        Participante participant = modelMapper.map(dto, Participante.class);

        // Delegar creación a la capa de servicio
        participantService.create(participant);

        // si esta bien retorna 201
        return ResponseEntity.status(201).build();
    }
}

