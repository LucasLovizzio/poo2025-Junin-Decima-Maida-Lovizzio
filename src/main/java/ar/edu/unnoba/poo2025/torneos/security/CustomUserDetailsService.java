package ar.edu.unnoba.poo2025.torneos.security;

import ar.edu.unnoba.poo2025.torneos.model.User;
import ar.edu.unnoba.poo2025.torneos.model.UserRole;
import ar.edu.unnoba.poo2025.torneos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
		                          .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return new CustomUserDetails(user, user.getRole());
	}

	public UserDetails loadUserByEmailAndRole(String email, UserRole expectedRole) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
		                          .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		UserRole userRole = user.getRole();
		if (userRole != expectedRole) {
			throw new UsernameNotFoundException("User found but role mismatch. Expected: " + expectedRole + ", Found: " + userRole);
		}

		return new CustomUserDetails(user, userRole);
	}

}
