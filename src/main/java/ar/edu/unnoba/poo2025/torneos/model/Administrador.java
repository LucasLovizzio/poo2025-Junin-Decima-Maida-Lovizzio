package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity @Table(name = "administradores")
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario {
    public Torneo crearTorneo() {
        return null;
    }

    public void editarTorneo(Torneo t) {}

    public void publicarTorneo(Torneo t) {}

    public void eliminarTorneo(Torneo t) {}

    public Competencia crearCompetencia(Torneo t) {
        return null;
    }

    public void editarCompetencia(Competencia c) {}

    public void eliminarCompetencia(Competencia c) {}

    public List<Participante> verInscriptos(Competencia c) {
        return null;
    }

    public Double verMontoRecaudado(Torneo t) {
        return null;
    }

}
