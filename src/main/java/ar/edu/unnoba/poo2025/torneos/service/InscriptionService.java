package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.Inscription;

import java.util.List;

public interface InscriptionService {

	List<Inscription> getInscriptionsByParticipantId(Long participantId);

	Inscription getInscriptionByIdAndParticipantId(Long inscriptionId, Long participantId);

}
