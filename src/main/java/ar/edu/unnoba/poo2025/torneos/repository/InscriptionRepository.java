package ar.edu.unnoba.poo2025.torneos.repository;

import ar.edu.unnoba.poo2025.torneos.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

	@Query("SELECT i FROM Inscription i " +
		"JOIN FETCH i.competition c " +
		"JOIN FETCH c.tournament t " +
		"WHERE i.participant.id = :participantId")
	List<Inscription> findAllByParticipantId(@Param("participantId") Long participantId);

	@Query("SELECT i FROM Inscription i " +
		"JOIN FETCH i.competition c " +
		"JOIN FETCH c.tournament t " +
		"WHERE i.id = :inscriptionId AND i.participant.id = :participantId")
	Optional<Inscription> findByIdAndParticipantId(@Param("inscriptionId") Long inscriptionId,
	                                               @Param("participantId") Long participantId);

	@Query("SELECT COUNT(i) > 0 FROM Inscription i " +
		"JOIN i.competition c " +
		"JOIN c.tournament t " +
		"WHERE i.participant.id = :participantId AND t.id = :tournamentId")
	boolean existsByParticipantIdAndTournamentId(@Param("participantId") Long participantId,
	                                             @Param("tournamentId") Long tournamentId);

	@Query("SELECT COUNT(i) FROM Inscription i WHERE i.competition.id = :competitionId")
	long countByCompetitionId(@Param("competitionId") Long competitionId);

	@Query("SELECT COUNT(i) > 0 FROM Inscription i " +
		"WHERE i.participant.id = :participantId AND i.competition.id = :competitionId")
	boolean existsByParticipantIdAndCompetitionId(@Param("participantId") Long participantId,
	                                              @Param("competitionId") Long competitionId);

}
