package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
	name = "users",
	uniqueConstraints = {@UniqueConstraint(columnNames = "email")}
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
	name = "user_type",
	discriminatorType = DiscriminatorType.STRING,
	length = 11
)
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false, unique = true, length = 255)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@OneToMany(mappedBy = "admin")
	private Set<Tournament> tournaments = new HashSet<>();

	protected User() {
	}

	protected User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public abstract UserRole getRole();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
