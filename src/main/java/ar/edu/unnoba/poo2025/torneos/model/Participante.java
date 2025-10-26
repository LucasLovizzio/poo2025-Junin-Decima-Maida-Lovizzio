package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "participantes")
@DiscriminatorValue("PARTICIPANTE")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Participante extends Usuario {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(name = "tipo_documento", nullable = false, length = 100)
    private String tipoDoc;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 100)
    private String nroDoc;

    @OneToMany(mappedBy = "participante")
    private Set<Inscripcion> inscripciones = new HashSet<Inscripcion>();

    public Participante() {}

    public Participante(String nombre, String apellido, String tipoDoc, String nroDoc, Set<Inscripcion> inscripciones) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.inscripciones = inscripciones;
    }

    public void registrarse() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public Set<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(Set<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
}
