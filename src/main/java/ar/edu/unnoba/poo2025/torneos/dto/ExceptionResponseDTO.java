package ar.edu.unnoba.poo2025.torneos.dto;

public class ExceptionResponseDTO {

	private String message;

	public ExceptionResponseDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
