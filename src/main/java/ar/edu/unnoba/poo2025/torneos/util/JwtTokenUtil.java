package ar.edu.unnoba.poo2025.torneos.util;

import ar.edu.unnoba.poo2025.torneos.model.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

	public static final String SECRET = "POO2025";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	private static final String ROLE_CLAIM = "role";

	public String generateToken(String subject, UserRole role) {
		return TOKEN_PREFIX + JWT.create()
		                         .withSubject(subject)
		                         .withClaim(ROLE_CLAIM, role.name())
		                         .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
		                         .sign(Algorithm.HMAC512(SECRET.getBytes()));
	}

	public boolean verify(String token) {
		try {
			JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
			   .build()
			   .verify(token.replace(TOKEN_PREFIX, ""));
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public String getSubject(String token) {
		return JWT.decode(token.replace(TOKEN_PREFIX, "")).getSubject();
	}

	public UserRole getRole(String token) {
		String roleString = JWT.decode(token.replace(TOKEN_PREFIX, "")).getClaim(ROLE_CLAIM).asString();
		return UserRole.valueOf(roleString);
	}

}