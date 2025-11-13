package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.AuthenticationFailedException;
import ar.edu.unnoba.poo2025.torneos.model.Participant;

public interface AuthenticationService {
    String authenticate(Participant participant) throws AuthenticationFailedException;
}
