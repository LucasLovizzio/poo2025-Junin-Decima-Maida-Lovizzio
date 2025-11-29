package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.CompetitionNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Inscription;
import ar.edu.unnoba.poo2025.torneos.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscriptionServiceImp implements InscriptionService {

	private final InscriptionRepository inscriptionRepository;

	@Autowired
	public InscriptionServiceImp(InscriptionRepository inscriptionRepository) {
		this.inscriptionRepository = inscriptionRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Inscription> getInscriptionsByParticipantId(Long participantId) {
		return inscriptionRepository.findAllByParticipantId(participantId);
	}

	@Override
	@Transactional(readOnly = true)
	public Inscription getInscriptionByIdAndParticipantId(Long inscriptionId, Long participantId) {
		return inscriptionRepository.findByIdAndParticipantId(inscriptionId, participantId)
		                            .orElseThrow(() -> new CompetitionNotFoundException("No se encontró la inscripción."));
	}

}
