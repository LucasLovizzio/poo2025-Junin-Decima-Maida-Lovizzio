package ar.edu.unnoba.poo2025.torneos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "BearerAuth";

		return new OpenAPI()
			.info(new Info()
				      .title("API REST POO 2025")
				      .version("1.0.0")
				      .description("Documentación de la API para el proyecto.\n\n" +
					                   "**Alumnos:**\n" +
					                   "* Decima Ulises\n" +
					                   "* Melina Maida\n" +
					                   "* Lucas Lovizzio"))
			// Agregamos la seguridad JWT globalmente
			.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
			.components(new Components()
				            .addSecuritySchemes(securitySchemeName,
				                                new SecurityScheme()
					                                .name(securitySchemeName)
					                                .type(SecurityScheme.Type.HTTP)
					                                .scheme("bearer")
					                                .bearerFormat("JWT")
				            ));
	}

}
