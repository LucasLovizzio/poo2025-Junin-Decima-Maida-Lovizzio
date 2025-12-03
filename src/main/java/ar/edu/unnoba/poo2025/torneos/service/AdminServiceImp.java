package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.exception.AdminAlreadyExistsException;
import ar.edu.unnoba.poo2025.torneos.exception.AdminNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.Admin;
import ar.edu.unnoba.poo2025.torneos.repository.AdminRepository;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImp implements AdminService {

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AdminServiceImp(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Admin findByEmail(String email) throws AdminNotFoundException {
		return adminRepository.findByEmail(email)
		                      .orElseThrow(() -> new AdminNotFoundException("Admin with email " + email + " does not exist."));
	}

	@Override
	public List<Admin> getUsersAdmin() {
		return adminRepository.findAll();
	}

	@Override
	public Admin create(Admin a) throws AdminAlreadyExistsException {
		// verificar si el admin ya existe, si existe lanzar una excepción, si no existe guardarlo.
		adminRepository.findByEmail(a.getEmail())
		               .ifPresent(existing -> {
			               throw new AdminAlreadyExistsException("Admin with email " + a.getEmail() + " already exists.");
		               });

		a.setPassword(passwordEncoder.encode(a.getPassword()));
		return adminRepository.save(a);
	}

	@Override
	public void delete(Long id, Long requesterId) {
		if (id.equals(requesterId)) {
			throw new IllegalArgumentException("An admin cannot delete their own account.");
		}
		if (!adminRepository.existsById(id)) {
			throw new AdminNotFoundException("Admin with id " + id + " does not exist.");
		}
		if (adminRepository.hasTournamentsAssociated(id)) {
			throw new IllegalStateException("Cannot delete admin with id " + id + " because they have associated tournaments.");
		}

		adminRepository.deleteById(id);
	}

}
