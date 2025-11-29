package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.AdminAlreadyExistsException;
import ar.edu.unnoba.poo2025.torneos.exception.AdminNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Admin;

import java.util.List;

public interface AdminService {

	Admin findByEmail(String email) throws AdminNotFoundException;
	List<Admin> getUsersAdmin() throws AdminNotFoundException;
	Admin create(Admin p) throws AdminAlreadyExistsException;
	void delete(Long id) throws AdminNotFoundException;

}
