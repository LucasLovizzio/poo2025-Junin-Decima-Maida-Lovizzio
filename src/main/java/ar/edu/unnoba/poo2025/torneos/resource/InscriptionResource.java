package ar.edu.unnoba.poo2025.torneos.resource;

import ar.edu.unnoba.poo2025.torneos.dto.InscriptionDetailResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscriptionResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.security.CustomUserDetails;
import ar.edu.unnoba.poo2025.torneos.service.InscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inscriptions")
public class InscriptionResource {

	private final InscriptionService inscriptionService;
	private final ModelMapper modelMapper;

	@Autowired
	public InscriptionResource(InscriptionService inscriptionService, ModelMapper modelMapper) {
		this.inscriptionService = inscriptionService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<List<InscriptionResponseDTO>> getParticipantInscriptions(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		Participant participant = (Participant) userDetails.getUser();

		List<InscriptionResponseDTO> inscriptions = inscriptionService
			.getInscriptionsByParticipantId(participant.getId())
			.stream()
			.map(inscription -> modelMapper.map(inscription, InscriptionResponseDTO.class))
			.collect(Collectors.toList());

		return ResponseEntity.ok(inscriptions);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InscriptionDetailResponseDTO> getParticipantInscriptionDetail(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long id) {

		Participant participant = (Participant) userDetails.getUser();

		InscriptionDetailResponseDTO dto = modelMapper.map(
			inscriptionService.getInscriptionByIdAndParticipantId(id, participant.getId()),
			InscriptionDetailResponseDTO.class
		);

		return ResponseEntity.ok(dto);
	}

}
