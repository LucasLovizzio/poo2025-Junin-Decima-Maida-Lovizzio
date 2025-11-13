package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import ar.edu.unnoba.poo2025.torneos.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TournamentServiceImp implements TournamentService {
    private final TournamentRepository tournamentRepository;
    @Autowired
    public TournamentServiceImp(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public List<Tournament> getPublishedTournaments() {
        return tournamentRepository.findByPublishedTrue();
    }
}
