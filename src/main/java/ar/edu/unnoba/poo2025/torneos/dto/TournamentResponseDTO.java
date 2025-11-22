package ar.edu.unnoba.poo2025.torneos.dto;

import ar.edu.unnoba.poo2025.torneos.model.Admin;
import ar.edu.unnoba.poo2025.torneos.model.Competition;

import java.time.LocalDateTime;
import java.util.Set;

public class TournamentResponseDTO {

	private Long id;
	private String name;
	private String description;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Boolean published;
	private Admin admin;
	private Set<Competition> competitions;

	public TournamentResponseDTO() {
	}

	public TournamentResponseDTO(Long id,
	                             String name,
	                             String description,
	                             LocalDateTime startDate,
	                             LocalDateTime endDate,
	                             Boolean published,
	                             Admin admin,
	                             Set<Competition> competitions) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.published = published;
		this.admin = admin;
		this.competitions = competitions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Set<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Set<Competition> competitions) {
		this.competitions = competitions;
	}

}


