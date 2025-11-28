package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetitionRequestDTO;
import ar.edu.unnoba.poo2025.torneos.exception.TournamentNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Admin;
import ar.edu.unnoba.poo2025.torneos.model.Competition;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TournamentService {

	@Transactional(readOnly = true)
	List<Tournament> getTournamentsOrderDesc();
	List<Tournament> getPublishedAndNextTournamentsOrInProgress() throws TournamentNotFoundException;
	Tournament getTournamentById(Long id) throws TournamentNotFoundException;
	List<Competition> getCompetitionsByTournamentId(Long tournamentId) throws TournamentNotFoundException;
	Competition getCompetitionByIdAndTournamentId(Long competitionId, Long tournamentId);

	void inscribeParticipantInCompetition(Participant p, Long tournamentId, Long competitionId);

	Tournament publish(Long tournamentId);

    void removeCompetition(Long tournamentId, Long competitionId);

	Competition changeTournamentCompetitionDetails(Long tournamentId, CompetitionRequestDTO competitionRequest, Admin admin);

	Competition createCompetition(Long tournamentId, CompetitionRequestDTO competitionRequest);
}
