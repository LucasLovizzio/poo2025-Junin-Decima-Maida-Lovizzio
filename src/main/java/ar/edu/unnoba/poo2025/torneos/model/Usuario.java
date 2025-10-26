package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity @Table(
        name = "usuarios",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
        name = "tipo_usuario_rol",
        discriminatorType = DiscriminatorType.STRING,
        length = 13
)
public abstract class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @OneToMany(mappedBy = "admin")
    private Set<Torneo> torneos = new HashSet<Torneo>();

    public Usuario() {}

    public Usuario(Long id, String email, String contrasena, Set<Torneo> torneos) {
        this.id = id;
        this.email = email;
        this.contrasena = contrasena;
        this.torneos = torneos;
    }

    public boolean autenticar() {
        return false;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Set<Torneo> getTorneos() {
        return torneos;
    }

    public void setTorneos(Set<Torneo> torneos) {
        this.torneos = torneos;
    }
}
