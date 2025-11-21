package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TournamentService {
    public List<Tournament> getPublishedTournaments();
    public List<Tournament> getTournamentsOrderDESC();
}
