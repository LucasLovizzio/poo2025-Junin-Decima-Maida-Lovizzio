package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.TournamentResponseOrderDTO;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import ar.edu.unnoba.poo2025.torneos.service.TournamentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminTournamentResource {
    private TournamentService service;
    private ModelMapper modelMapper;

    @Autowired
    public AdminTournamentResource(TournamentService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/tournaments")
    public ResponseEntity<List<TournamentResponseOrderDTO>> getTournaments() {
        // Obtener torneos ordenados por fecha DESC
        List<Tournament> tournaments = service.getTournamentsOrderDESC();
        // Mapear al DTO con fechas y publicado
        List<TournamentResponseOrderDTO> tournamentDTOs = tournaments.stream()
                .map(tournament -> modelMapper.map(tournament, TournamentResponseOrderDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tournamentDTOs);
    }
}
