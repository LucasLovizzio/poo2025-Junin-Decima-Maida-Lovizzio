package com.example.demo;

import java.util.Date;

public class Inscripcion {
    private Double precioFinal;
    private Date fechaInscripcion;

    public Inscripcion(Double precioFinal, Date fechaInscripcion) {
        this.precioFinal = precioFinal;
        this.fechaInscripcion = fechaInscripcion;
    }

    public void calcularPrecio() {}

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
