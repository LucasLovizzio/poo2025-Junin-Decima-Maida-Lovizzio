package ar.edu.unnoba.poo2025.torneos.repository;

import ar.edu.unnoba.poo2025.torneos.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	@Query("SELECT p FROM Admin p WHERE p.email = :email")
	Optional<Admin> findByEmail(@Param("email") String email);
	@Query("""
		SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
		FROM Tournament t
		WHERE t.admin.id = :adminId
		""")
	boolean hasTournamentsAssociated(@Param("adminId") Long adminId);

}
