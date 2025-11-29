package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;

public class CompetitionRequestDTO {

    private String name;
    private BigDecimal basePrice;
    private Integer capacity;

    public CompetitionRequestDTO() {}

    public CompetitionRequestDTO(String name, BigDecimal basePrice, Integer capacity) {
        this.name = name;
        this.basePrice = basePrice;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
