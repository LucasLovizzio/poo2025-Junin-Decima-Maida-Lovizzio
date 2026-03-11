package ar.edu.unnoba.poo2025.torneos.config;

import ar.edu.unnoba.poo2025.torneos.model.UserRole;
import ar.edu.unnoba.poo2025.torneos.security.JwtAccessDeniedHandler;
import ar.edu.unnoba.poo2025.torneos.security.JwtAuthenticationEntryPoint;
import ar.edu.unnoba.poo2025.torneos.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.View;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Autowired
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
	                      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
	                      JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, View error) throws Exception {
		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/admin/auth").permitAll()
				.requestMatchers("/auth").permitAll()
				.requestMatchers("/account").permitAll()
				.requestMatchers("/error").permitAll()
				// .requestMatchers("/doc/**").permitAll() // En caso de utilizar un alias para la documentación
				.requestMatchers("/v3/api-docs/**").permitAll()
				.requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())

				.requestMatchers("/tournaments/**").hasRole(UserRole.PARTICIPANT.name())
				.requestMatchers("/inscriptions/**").hasRole(UserRole.PARTICIPANT.name())

				.anyRequest().authenticated()
			)
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of("http://localhost:*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


}