package ar.edu.unnoba.poo2025.torneos.repository;

import ar.edu.unnoba.poo2025.torneos.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository <Participant, Long> {

	@Query("SELECT p FROM Participant p WHERE p.email = :email")
	Optional <Participant> findByEmail(@Param("email") String email);

	@Query("SELECT p FROM Participant p WHERE p.docType = :docType AND p.docNumber = :docNumber")
	Optional <Participant> findByDocTypeAndDocNumber(@Param("docType") String docType,
	                                                 @Param("docNumber") String docNumber);

	@Query("SELECT p FROM Participant p WHERE p.email = :email OR (p.docType = :docType AND p.docNumber = :docNumber)")
	List <Participant> findByEmailOrDocTypeAndDocNumber(String email, String docType, String docNumber);

}
