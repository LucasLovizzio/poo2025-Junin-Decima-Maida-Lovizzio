package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
	name = "participants",
	uniqueConstraints = @UniqueConstraint(
		name = "uk_document",
		columnNames = {"document_number", "document_type"}
	)
)
@DiscriminatorValue("PARTICIPANT")
@PrimaryKeyJoinColumn(name = "user_id")
public class Participant extends User {

	@Column(length = 100)
	private String name;

	@Column(length = 100)
	private String lastName;

	@Column(name = "document_type", length = 100)
	private String docType;

	@Column(name = "document_number", length = 100)
	private String docNumber;

	@OneToMany(mappedBy = "participant")
	private Set<Inscription> inscriptions = new HashSet<>();

	public Participant() {
	}

	public Participant(String name, String lastName, String docType, String docNumber, Set<Inscription> inscriptions) {
		this.name = name;
		this.lastName = lastName;
		this.docType = docType;
		this.docNumber = docNumber;
		this.inscriptions = inscriptions;
	}

	@Override
	public UserRole getRole() {
		return UserRole.PARTICIPANT;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public Set<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Set<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}

}
