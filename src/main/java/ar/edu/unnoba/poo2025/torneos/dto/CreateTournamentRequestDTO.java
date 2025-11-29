package ar.edu.unnoba.poo2025.torneos.dto;

import java.time.LocalDateTime;

public class CreateTournamentRequestDTO {

	private String name;
	private String description;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public CreateTournamentRequestDTO() {
	}

	public CreateTournamentRequestDTO(String name, String description, LocalDateTime startDate, LocalDateTime endDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

}
