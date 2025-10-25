package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.ParticipantNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Participante;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipantRepository;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;
import ar.edu.unnoba.poo2025.torneos.exception.ParticipantAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantServiceImp implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ParticipantServiceImp(ParticipantRepository participantRepository, PasswordEncoder passwordEncoder) {
        this.participantRepository = participantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Participante create(Participante p) throws ParticipantAlreadyExistsException {
        // verificar si el participante ya existe, si existe lanzar una excepción, si no existe guardarlo.
        if(participantRepository.findByEmail(p.getEmail()) != null){
            throw new ParticipantAlreadyExistsException("Participant with email " + p.getEmail() + " already exists.");
        }
        p.setPassword(passwordEncoder.encode(p.getPassword()));
        return participantRepository.save(p);
    }

    @Override
    public void delete(Participante p) throws ParticipantNotFoundException {
        // verificar si el participante existe, si no existe lanzar una excepción, si existe eliminarlo.
        if(participantRepository.findByEmail(p.getEmail()) == null){
            throw new ParticipantNotFoundException("Participant with email " + p.getEmail() + " does not exist.");
        }
        participantRepository.delete(p);
    }


    // PUT / actualizar un participante
    @Override
    public Participante update(Participante p) throws ParticipantNotFoundException {
        // verificar si el participante existe, si no existe lanzar una excepción
        Participante existingParticipant = participantRepository.findByEmail(p.getEmail());
        if(existingParticipant == null){
            throw new ParticipantNotFoundException("Participant with email " + p.getEmail() + " does not exist.");
        }
        // actualizar los campos del participante existente con los del participante p
        existingParticipant.setNombre(p.getNombre());
        existingParticipant.setApellido(p.getApellido());
        existingParticipant.setPassword(passwordEncoder.encode(p.getPassword()));
        return participantRepository.save(existingParticipant);
    }
}
