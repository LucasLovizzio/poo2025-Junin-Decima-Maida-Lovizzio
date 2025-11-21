package ar.edu.unnoba.poo2025.torneos.repository;

import ar.edu.unnoba.poo2025.torneos.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository< Tournament, Long>{
    List<Tournament> findByPublishedTrue();
    List<Tournament> findAllByOrderByStartDateDesc();

    @Query("SELECT t FROM Tournament t ORDER BY t.startDate DESC")
    List<Tournament> getTournamentsOrderDESC();
}
