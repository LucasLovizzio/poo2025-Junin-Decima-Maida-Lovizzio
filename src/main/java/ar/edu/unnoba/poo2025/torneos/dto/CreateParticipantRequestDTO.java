package ar.edu.unnoba.poo2025.torneos.dto;

import ar.edu.unnoba.poo2025.torneos.validation.ValidDocType;
import jakarta.validation.constraints.Email;

public class CreateParticipantRequestDTO {

	@Email(message = "Invalid email format")
	private String email;
	private String password;
	private String name;
	private String lastName;
	@ValidDocType
	private String docType;
	private String docNumber;

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
