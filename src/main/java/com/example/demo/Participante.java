package com.example.demo;

import java.util.Set;

public class Participante {
    private String nombre;
    private String apellido;
    private String tipoDoc;
    private String nroDoc;

    private Set<Inscripcion> inscripciones;

    public Participante(String nombre, String apellido, String tipoDoc, String nroDoc, Set<Inscripcion> inscripciones) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.inscripciones = inscripciones;
    }

    public void registrarse() {}

    public Inscripcion inscribirse(Competencia c) {
        return null;
    }
}
