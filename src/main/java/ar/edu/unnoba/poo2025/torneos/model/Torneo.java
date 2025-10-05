package ar.edu.unnoba.poo2025.torneos.model;

import java.util.Date;
import java.util.Set;

public class Torneo {
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Boolean publicado;

    private Set<Competencia> competencias;

    public Torneo(String nombre, String descripcion, Date fechaInicio, Date fechaFin, Boolean publicado, Set<Competencia> competencias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.publicado = publicado;
        this.competencias = competencias;
    }

    public void agregarCompetencia(Competencia c) {}
    public void editar() {}
    public void eliminar() {}

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    public Set<Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Set<Competencia> competencias) {
        this.competencias = competencias;
    }
}

