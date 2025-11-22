package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InscriptionResponseDTO {

	private Long id;
	private LocalDateTime inscriptionDate;
	private BigDecimal finalPrice;
	private Long tournamentId;
	private String tournamentName;
	private Long competitionId;
	private String competitionName;

	public InscriptionResponseDTO() {
	}

	public InscriptionResponseDTO(Long id, LocalDateTime inscriptionDate, BigDecimal finalPrice, Long tournamentId, String tournamentName, Long competitionId, String competitionName) {
		this.id = id;
		this.inscriptionDate = inscriptionDate;
		this.finalPrice = finalPrice;
		this.tournamentId = tournamentId;
		this.tournamentName = tournamentName;
		this.competitionId = competitionId;
		this.competitionName = competitionName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getInscriptionDate() {
		return inscriptionDate;
	}

	public void setInscriptionDate(LocalDateTime inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public Long getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(Long competitionId) {
		this.competitionId = competitionId;
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}

}
