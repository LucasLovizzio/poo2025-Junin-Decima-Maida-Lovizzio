package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.TournamentResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import ar.edu.unnoba.poo2025.torneos.service.AuthorizationService;
import ar.edu.unnoba.poo2025.torneos.service.TournamentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tournaments")
public class TournamentResource {
    private TournamentService service;
    private ModelMapper modelMapper;
    private AuthorizationService authorizationService;


    @Autowired
    public TournamentResource(TournamentService service, ModelMapper modelMapper, AuthorizationService authorizationService) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.authorizationService = authorizationService;
    }
    @GetMapping
    public ResponseEntity<List<TournamentResponseDTO>> getTournaments() {
        // Obtiene los torneos publicados ordenados
        List<Tournament> tournaments = service.getTournamentsOrderDESC();

        // Mapea entidades a DTOs
        List<TournamentResponseDTO> tournamentDTOs = tournaments.stream()
                .map(tournament -> modelMapper.map(tournament, TournamentResponseDTO.class))
                .collect(Collectors.toList());

        // Devuelve la lista con 200 OK
        return ResponseEntity.ok(tournamentDTOs);
    }
}

