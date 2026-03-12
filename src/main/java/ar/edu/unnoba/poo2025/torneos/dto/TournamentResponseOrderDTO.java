package ar.edu.unnoba.poo2025.torneos.dto;

import java.time.LocalDateTime;

public class TournamentResponseOrderDTO {

    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String published;


    //(necesario para frameworks como Jackson)
    public TournamentResponseOrderDTO() {
    }

    public TournamentResponseOrderDTO(Long id, String name, LocalDateTime startDate, LocalDateTime endDate, Boolean published) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.published = published ? "Publicado" : "Despublicado";
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

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }
}


