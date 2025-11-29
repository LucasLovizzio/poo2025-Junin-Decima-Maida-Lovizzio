package ar.edu.unnoba.poo2025.torneos.dto;

public class DeleteAdminResponseDTO {

	private String message;

	public DeleteAdminResponseDTO() {
	}

	public DeleteAdminResponseDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
