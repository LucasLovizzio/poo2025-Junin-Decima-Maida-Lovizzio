package ar.edu.unnoba.poo2025.torneos.service;

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
    public String authenticate(Participant participant) throws Exception {

        Participant p = participantService.findByEmail(participant.getEmail());
        if (p == null) throw new Exception("Participant not found");

        passwordEncoder.verify(participant.getPassword(), p.getPassword());

        return jwtTokenUtil.generateToken(p.getEmail());
    }
}
