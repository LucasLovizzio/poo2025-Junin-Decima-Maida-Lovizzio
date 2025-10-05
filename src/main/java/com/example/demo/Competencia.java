package com.example.demo;

public class Competencia {
    private String nombre;
    private Double precioBase;
    private Integer cupo;

    private Inscripcion inscripcion;

    public Competencia(String nombre, Double precioBase, Integer cupo, Inscripcion inscripcion) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.cupo = cupo;
        this.inscripcion = inscripcion;
    }

    public Inscripcion inscribir(Participante p) {
        return inscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }
}
