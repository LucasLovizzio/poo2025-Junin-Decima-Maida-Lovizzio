package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.ParticipantAlreadyExistsException;
import ar.edu.unnoba.poo2025.torneos.exception.ParticipantNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Participante;

public interface ParticipantService {

    Participante create(Participante p) throws ParticipantAlreadyExistsException;
    void delete(Participante p) throws ParticipantNotFoundException;
    Participante update(Participante p) throws ParticipantNotFoundException;

}
