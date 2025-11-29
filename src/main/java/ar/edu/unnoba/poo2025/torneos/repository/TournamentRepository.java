package ar.edu.unnoba.poo2025.torneos.repository;

import ar.edu.unnoba.poo2025.torneos.model.Competition;
import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

	@Query("SELECT t FROM Tournament t ORDER BY t.startDate DESC")
	List<Tournament> getTournamentsOrderDESC();

	@Query("""
		SELECT t
		FROM Tournament t
		WHERE t.published = true
		AND t.endDate > CURRENT_DATE
		""")
	List<Tournament> findPublishedAndNextTournamentsOrInProgress();

	@Query("""
		SELECT t
		FROM Tournament t
		WHERE t.id = :id
		AND t.published = true
		""")
	Optional<Tournament> findByIdAndPublished(Long id);
	@Query("""
		SELECT c
		FROM Competition c
		JOIN Tournament t on c.tournament.id = t.id
		WHERE t.id = :tournamentId
		AND t.published = true
		""")
	List<Competition> findCompetitionsByTournamentId(Long tournamentId);

	@Query("""
		SELECT c
		FROM Competition c
		JOIN Tournament t on c.tournament.id = t.id
		WHERE c.id = :competitionId
		AND t.id = :tournamentId
		AND t.published = true
		""")
	Optional<Competition> findCompetitionByIdAndTournamentId(Long competitionId, Long tournamentId);

}
