package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetitionRequestDTO;
import ar.edu.unnoba.poo2025.torneos.exception.CompetitionFullException;
import ar.edu.unnoba.poo2025.torneos.exception.CompetitionNotFoundException;
import ar.edu.unnoba.poo2025.torneos.exception.ParticipantAlredyInscribedInTournamentException;
import ar.edu.unnoba.poo2025.torneos.exception.TournamentNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.*;
import ar.edu.unnoba.poo2025.torneos.repository.CompetitionRepository;
import ar.edu.unnoba.poo2025.torneos.repository.InscriptionRepository;
import ar.edu.unnoba.poo2025.torneos.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TournamentServiceImp implements TournamentService {

	private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.50");

	private final TournamentRepository tournamentRepository;
	private final InscriptionRepository inscriptionRepository;
	private final CompetitionRepository competitionRepository;

	@Autowired
	public TournamentServiceImp(TournamentRepository tournamentRepository,
								InscriptionRepository inscriptionRepository, CompetitionRepository competitionRepository) {
		this.tournamentRepository = tournamentRepository;
		this.inscriptionRepository = inscriptionRepository;
		this.competitionRepository = competitionRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tournament> getTournamentsOrderDesc() {
		return tournamentRepository.getTournamentsOrderDESC();
	}

	// Obtiene la lista de torneos publicados y próximos o en progreso
	@Override
	@Transactional(readOnly = true)
	public List<Tournament> getPublishedAndNextTournamentsOrInProgress() {
		List<Tournament> tournaments = tournamentRepository.findPublishedAndNextTournamentsOrInProgress();
		if (tournaments.isEmpty()) {
			throw new TournamentNotFoundException("No se encontraron torneos publicados o en progreso.");
		}

		return tournaments;
	}

	// Obtiene un torneo por ID
	@Override
	@Transactional(readOnly = true)
	public Tournament getTournamentById(Long id) throws TournamentNotFoundException {
		// busca tambien que el torneo este publicado
		return tournamentRepository.findByIdAndPublished(id)
		                           .orElseThrow(() -> new TournamentNotFoundException("No se ha podido encontrar el torneo."));
	}

	// Obtiene las competencias de un torneo por ID de torneo
	@Override
	@Transactional(readOnly = true)
	public List<Competition> getCompetitionsByTournamentId(Long tournamentId) throws TournamentNotFoundException {
		List<Competition> competitions = tournamentRepository.findCompetitionsByTournamentId(tournamentId);
		if (competitions.isEmpty()) {
			throw new TournamentNotFoundException("No se han podido encontrar competencias para el torneo.");
		}

		return competitions;
	}

	// Obtiene una competencia por ID de competencia y ID de torneo
	@Override
	@Transactional(readOnly = true)
	public Competition getCompetitionByIdAndTournamentId(Long competitionId, Long tournamentId) {
		return tournamentRepository.findCompetitionByIdAndTournamentId(competitionId, tournamentId)
		                           .orElseThrow(() -> new CompetitionNotFoundException("No se ha podido encontrar la competencia en el torneo."));
	}

	@Override
	@Transactional
	public void inscribeParticipantInCompetition(Participant participant, Long tournamentId, Long competitionId) {
		Competition competition = tournamentRepository.findCompetitionByIdAndTournamentId(competitionId, tournamentId)
		                                              .orElseThrow(() -> new CompetitionNotFoundException("No se ha podido encontrar la competencia en el torneo."));

		if (competition.getCapacity() <= competition.getInscriptions().size()) {
			throw new CompetitionFullException("La competencia ha alcanzado su capacidad máxima.");
		}

		// Verificar si el participante ya está inscrito en la competencia
		boolean alreadyInscribedInCompetition = competition.getInscriptions().stream()
		                                                   .anyMatch(inscription -> inscription.getParticipant().getId().equals(participant.getId()));
		if (alreadyInscribedInCompetition) {
			throw new ParticipantAlredyInscribedInTournamentException(
				"El participante ya está inscrito en esta competencia.");
		}

		Inscription inscription = new Inscription();
		inscription.setParticipant(participant);
		inscription.setCompetition(competition);
		BigDecimal finalPrice = calculateFinalPrice(participant.getId(), tournamentId, competition.getBasePrice());
		inscription.setFinalPrice(finalPrice);
		inscription.setInscriptionDate(LocalDateTime.now());

		inscriptionRepository.save(inscription);
	}

	@Override @Transactional
	public Tournament publish(Long tournamentId) {
		Tournament tournament = getTournamentById(tournamentId);

		tournament.setPublished(true);
		return tournamentRepository.save(tournament);
	}

	@Override @Transactional
	public void removeCompetition(Long tournamentId, Long competitionId) {
		Tournament tournament = getTournamentById(tournamentId);
		Competition competition = getCompetitionByIdAndTournamentId(competitionId, tournamentId);

		tournament.getCompetitions().remove(competition);
		tournamentRepository.save(tournament);
	}

	@Override @Transactional
	public Competition changeTournamentCompetitionDetails(Long competitionId, CompetitionRequestDTO competitionRequest, Admin admin) {

		Competition competition = competitionRepository.findById(competitionId)
				.orElseThrow(CompetitionNotFoundException::new);

		Tournament tournament = competition.getTournament();

		if (!tournament.getAdmin().getId().equals(admin.getId()))
			throw new AccessDeniedException("No tiene permisos para modificar esta competencia.");

		// Validaciones
		if (competitionRequest.getBasePrice() != null && competitionRequest.getBasePrice().compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("El precio debe ser mayor que 0");

		if (competitionRequest.getCapacity() != null && competitionRequest.getCapacity() <= 0)
			throw new IllegalArgumentException("El capacidad debe ser mayor que 0");

		if (competitionRequest.getName() != null && competitionRequest.getName().isEmpty())
			throw new IllegalArgumentException("El nombre debe no puede ser vacio.");

		if (competitionRequest.getBasePrice() != null)
			competition.setBasePrice(competitionRequest.getBasePrice());

		if (competitionRequest.getCapacity() != null)
			competition.setCapacity(competitionRequest.getCapacity());

		if (competitionRequest.getName() != null)
			competition.setName(competitionRequest.getName());

		return competitionRepository.save(competition);
	}

	@Override @Transactional
	public Competition createCompetition(Long tournamentId, CompetitionRequestDTO competitionRequest) {
		Tournament tournament = getTournamentById(tournamentId);

		if (competitionRequest.getCapacity() == null || competitionRequest.getCapacity() <= 0)
			throw new IllegalArgumentException("La capacidad debe ser mayor que 0");

		if (competitionRequest.getBasePrice() == null || competitionRequest.getBasePrice().compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("El precio debe ser mayor que 0");

		if (competitionRequest.getName() == null || competitionRequest.getName().trim().isEmpty())
			throw new IllegalArgumentException("El nombre de la competencia no puede ser nulo");

		Competition competition = new Competition();
		competition.setName(competitionRequest.getName());
		competition.setBasePrice(competitionRequest.getBasePrice());
		competition.setCapacity(competitionRequest.getCapacity());
		competition.setTournament(tournament);

		tournament.getCompetitions().add(competition);

		return competitionRepository.save(competition);

	}

	private BigDecimal calculateFinalPrice(Long participantId, Long tournamentId, BigDecimal basePrice) {
		boolean hasPreviousInscriptions = tournamentRepository.findById(tournamentId)
		                                                      .orElseThrow(() -> new TournamentNotFoundException("No se ha podido encontrar el torneo."))
		                                                      .getCompetitions()
		                                                      .stream()
		                                                      .flatMap(comp -> comp.getInscriptions().stream())
		                                                      .anyMatch(inscription -> inscription.getParticipant().getId().equals(participantId));

		if (hasPreviousInscriptions) {
			return basePrice.multiply(DISCOUNT_RATE);
		}

		return basePrice;
	}

}
