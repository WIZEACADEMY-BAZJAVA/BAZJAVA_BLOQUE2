package com.wizeline.maven.LearningJava.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "${info.app.name}", version = "${info.app.java.version}",
                contact = @Contact(name = "Developer", email = "developer@wizeline.com",
                        url = "https://www.wizeline.com/")),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development"),
        })
public class OpenAPIConfiguration {

    private final String SECURITY_SCHEME_NAME = "JWT Token";

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description(
                                        "Inserta el token generado. Se obtiene en el apartado de autenticación.")
                                .bearerFormat("JWT")));

    }
}
