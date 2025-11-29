package ar.edu.unnoba.poo2025.torneos.dto;

public class CreateAdminResponseDTO {


    private Long id;
    private String email;

    public CreateAdminResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
