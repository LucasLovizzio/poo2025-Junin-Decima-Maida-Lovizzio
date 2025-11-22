package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

	@OneToMany(mappedBy = "admin")
	private Set<Tournament> tournaments = new HashSet<>();

	public Admin() {
	}

	public Admin(String email, String password) {
		super(email, password);
	}

	@Override
	public UserRole getRole() {
		return UserRole.ADMIN;
	}

	public Set<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(Set<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

}
