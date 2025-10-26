package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity @Table(name = "torneos")
public class Torneo {

    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Lob @Column(nullable = false)
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(nullable = false)
    private Boolean publicado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_creador_id", nullable = false)
    private Usuario admin;

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Competencia> competencias;

    public Torneo() {}

    public Torneo(Long id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, Boolean publicado, Usuario admin, Set<Competencia> competencias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.publicado = publicado;
        this.admin = admin;
        this.competencias = competencias;
    }

    public void agregarCompetencia(Competencia c) {}
    public void editar() {}
    public void eliminar() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    public Usuario getAdmin() {
        return admin;
    }

    public void setAdmin(Usuario admin) {
        this.admin = admin;
    }

    public Set<Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Set<Competencia> competencias) {
        this.competencias = competencias;
    }
}

