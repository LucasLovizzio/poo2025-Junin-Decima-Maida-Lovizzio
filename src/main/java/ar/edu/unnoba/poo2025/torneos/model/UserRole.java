package ar.edu.unnoba.poo2025.torneos.model;

public enum UserRole {
	ADMIN("ROLE_ADMIN"),
	PARTICIPANT("ROLE_PARTICIPANT");

	private final String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}
}
