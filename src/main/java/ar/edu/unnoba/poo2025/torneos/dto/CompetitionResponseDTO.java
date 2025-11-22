package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;

public class CompetitionResponseDTO {

	private Long id;
	private String name;
	private BigDecimal basePrice;
	private Integer capacity;
	private String category;

	public CompetitionResponseDTO() {
	}

	public CompetitionResponseDTO(Long id, String name, BigDecimal basePrice, Integer capacity, String category) {
		this.id = id;
		this.name = name;
		this.basePrice = basePrice;
		this.capacity = capacity;
		this.category = category;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
