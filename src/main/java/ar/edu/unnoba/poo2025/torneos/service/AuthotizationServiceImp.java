package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.AuthorizationFailedException;
import ar.edu.unnoba.poo2025.torneos.exception.ParticipantNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthotizationServiceImp {

	private final JwtTokenUtil jwtTokenUtil;
	private final ParticipantService participantService;

	@Autowired
	public AuthorizationServiceImp(JwtTokenUtil jwtTokenUtil, ParticipantService participantService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.participantService = participantService;
	}

	@Override
	public Participant authorize(String token) throws AuthorizationFailedException {
		if (token == null || token.isEmpty()) {
			throw new AuthorizationFailedException("Authorization failed: Token is missing");
		}

		try {
			if (!jwtTokenUtil.verifyToken(token)) {
				throw new AuthorizationFailedException("Authorization failed: Invalid token");
			}

			String email = jwtTokenUtil.getSubject(token);
			return participantService.findByEmail(email);
		} catch (ParticipantNotFoundException e) {
			throw new AuthorizationFailedException("Authorization failed: User not found");
		} catch (Exception e) {
			throw new AuthorizationFailedException("Authorization failed: " + e.getMessage());
		}
	}

}
