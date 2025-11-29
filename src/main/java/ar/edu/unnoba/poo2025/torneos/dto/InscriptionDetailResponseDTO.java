package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InscriptionDetailResponseDTO {

	private Long inscriptionId;
	private BigDecimal finalPrice;
	private LocalDateTime inscriptionDate;
	private Long competitionId;
	private String competitionName;
	private Long tournamentId;
	private String tournamentName;
	private String tournamentDescription;
	private LocalDateTime tournamentStartDate;
	private LocalDateTime tournamentFinishDate;

	public InscriptionDetailResponseDTO() {
	}

	public Long getInscriptionId() {
		return inscriptionId;
	}

	public void setInscriptionId(Long inscriptionId) {
		this.inscriptionId = inscriptionId;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public LocalDateTime getInscriptionDate() {
		return inscriptionDate;
	}

	public void setInscriptionDate(LocalDateTime inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
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

	public String getTournamentDescription() {
		return tournamentDescription;
	}

	public void setTournamentDescription(String tournamentDescription) {
		this.tournamentDescription = tournamentDescription;
	}

	public LocalDateTime getTournamentStartDate() {
		return tournamentStartDate;
	}

	public void setTournamentStartDate(LocalDateTime tournamentStartDate) {
		this.tournamentStartDate = tournamentStartDate;
	}

	public LocalDateTime getTournamentFinishDate() {
		return tournamentFinishDate;
	}

	public void setTournamentFinishDate(LocalDateTime tournamentFinishDate) {
		this.tournamentFinishDate = tournamentFinishDate;
	}

}
