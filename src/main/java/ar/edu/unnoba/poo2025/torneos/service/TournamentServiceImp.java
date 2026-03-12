package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetitionRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TournamentResponseDTO;
import ar.edu.unnoba.poo2025.torneos.exception.CompetitionFullException;
import ar.edu.unnoba.poo2025.torneos.exception.CompetitionNotFoundException;
import ar.edu.unnoba.poo2025.torneos.exception.ParticipantAlredyInscribedInTournamentException;
import ar.edu.unnoba.poo2025.torneos.exception.TournamentNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.*;
import ar.edu.unnoba.poo2025.torneos.repository.AdminRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImp implements TournamentService {

	private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.50");

	private final TournamentRepository tournamentRepository;
	private final InscriptionRepository inscriptionRepository;
	private final CompetitionRepository competitionRepository;
	private final AdminRepository adminRepository;

	@Autowired
	public TournamentServiceImp(TournamentRepository tournamentRepository,
	                            InscriptionRepository inscriptionRepository,
	                            CompetitionRepository competitionRepository,
	                            AdminRepository adminRepository) {
		this.tournamentRepository = tournamentRepository;
		this.inscriptionRepository = inscriptionRepository;
		this.competitionRepository = competitionRepository;
		this.adminRepository = adminRepository;
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
		return tournamentRepository.findPublishedAndNextTournamentsOrInProgress();
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
	public List<Competition> getCompetitionsByTournamentId(Long tournamentId) {
		return tournamentRepository.findCompetitionsByTournamentId(tournamentId);
	}

	// Obtiene una competencia por ID de competencia y ID de torneo (solo torneos publicados, para participantes)
	@Override
	@Transactional(readOnly = true)
	public Competition getCompetitionByIdAndTournamentId(Long competitionId, Long tournamentId) {
		return tournamentRepository.findCompetitionByIdAndTournamentId(competitionId, tournamentId)
		                           .orElseThrow(() -> new CompetitionNotFoundException("No se ha podido encontrar la competencia en el torneo."));
	}

	// Obtiene una competencia por ID de competencia y ID de torneo (sin filtro published, para admins)
	@Override
	@Transactional(readOnly = true)
	public Competition getCompetitionByIdAndTournamentIdAdmin(Long competitionId, Long tournamentId) {
		return tournamentRepository.findCompetitionByIdAndTournamentIdAdmin(competitionId, tournamentId)
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

	@Override
	@Transactional
	public Tournament createTournament(Tournament tournament, Admin admin) {
		Admin existingAdmin = adminRepository.findById(admin.getId())
		                                     .orElseThrow(() -> new IllegalArgumentException("Admin no encontrado"));
		tournament.setAdmin(existingAdmin);
		existingAdmin.getTournaments().add(tournament);
		return tournamentRepository.save(tournament);
	}

	@Override
	@Transactional
	public Tournament publish(Long tournamentId) {
		Tournament tournament = tournamentRepository.findById(tournamentId)
		                                            .orElseThrow(() -> new TournamentNotFoundException("No se ha podido encontrar el torneo."));

		tournament.setPublished(true);
		return tournamentRepository.save(tournament);
	}

	@Override
	@Transactional
	public void removeCompetition(Long tournamentId, Long competitionId) {
		Tournament tournament = tournamentRepository.findById(tournamentId)
		                                            .orElseThrow(() -> new TournamentNotFoundException("No se ha podido encontrar el torneo."));
		Competition competition = tournamentRepository.findCompetitionByIdAndTournamentIdAdmin(competitionId, tournamentId)
		                                              .orElseThrow(() -> new CompetitionNotFoundException("No se ha podido encontrar la competencia en el torneo."));

		if (!tournament.getCompetitions().contains(competition)) {
			throw new CompetitionNotFoundException("La competencia no pertenece al torneo especificado.");
		} else if (!competition.getInscriptions().isEmpty()) {
			throw new IllegalStateException("No se puede eliminar una competencia con inscripciones.");
		}

		tournament.getCompetitions().remove(competition);
		tournamentRepository.save(tournament);
	}

	@Override
	@Transactional
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

	@Override
	@Transactional(readOnly = true)
	public List<Competition> getAdminCompetitionsByTournamentId(Long tournamentId) {
		return tournamentRepository.findAllCompetitionsByTournamentId(tournamentId);
	}

	@Override
	@Transactional
	public Competition createCompetition(Long tournamentId, CompetitionRequestDTO competitionRequest) {
		Tournament tournament = tournamentRepository.findById(tournamentId)
		                                            .orElseThrow(() -> new TournamentNotFoundException("No se ha podido encontrar el torneo."));

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

	public TournamentResponseDTO convertToDto(Tournament tournament) {
		TournamentResponseDTO dto = new TournamentResponseDTO();
		dto.setId(tournament.getId());
		dto.setName(tournament.getName());
		dto.setDescription(tournament.getDescription());
		dto.setStartDate(tournament.getStartDate());
		dto.setEndDate(tournament.getEndDate());
		dto.setPublished(tournament.getPublished());

		// Mapea solo el ID del admin
		dto.setAdminId(tournament.getAdmin() != null ? tournament.getAdmin().getId() : null);

		// Mapea solo los IDs de las competitions
		if (tournament.getCompetitions() != null) {
			Set<Long> competitionIds = tournament.getCompetitions().stream()
			                                     .map(Competition::getId)
			                                     .collect(Collectors.toSet());
			dto.setCompetitionsIds(competitionIds);
		}

		return dto;
	}

}
