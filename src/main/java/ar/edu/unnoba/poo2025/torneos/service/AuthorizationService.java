package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.AuthorizationFailedException;
import ar.edu.unnoba.poo2025.torneos.model.Participant;

public interface AuthorizationService {

    Participant authorize(String token) throws AuthorizationFailedException;

}