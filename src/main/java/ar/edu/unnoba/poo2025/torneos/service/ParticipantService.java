package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.ParticipantAlreadyExistsException;
import ar.edu.unnoba.poo2025.torneos.exception.ParticipantNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Participant;

public interface ParticipantService {

	Participant create(Participant p) throws ParticipantAlreadyExistsException;
	void delete(Participant p) throws ParticipantNotFoundException;
	Participant update(Participant p) throws ParticipantNotFoundException;

	Participant findByEmail(String email) throws ParticipantNotFoundException;

    Participant findByEmail(String email) throws ParticipantNotFoundException;
}
