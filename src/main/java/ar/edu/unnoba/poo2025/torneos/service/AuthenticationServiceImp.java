package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.AuthenticationFailedException;
import ar.edu.unnoba.poo2025.torneos.exception.ParticipantNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Participant;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private final ParticipantService participantService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationServiceImp(ParticipantService participantService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.participantService = participantService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public String authenticate(Participant participant) throws AuthenticationFailedException {
        // validar entrada: null y valores en blanco
        if (participant == null || isBlank(participant.getEmail()) || isBlank(participant.getPassword())) {
            throw new AuthenticationFailedException();
        }

        try {
            Participant p = participantService.findByEmail(participant.getEmail());

            boolean matches = passwordEncoder.verify(participant.getPassword(), p.getPassword());
            if (!matches) {
                throw new AuthenticationFailedException();
            }
            return jwtTokenUtil.generateToken(p.getEmail());

        } catch (ParticipantNotFoundException e) {
            throw new AuthenticationFailedException();
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
