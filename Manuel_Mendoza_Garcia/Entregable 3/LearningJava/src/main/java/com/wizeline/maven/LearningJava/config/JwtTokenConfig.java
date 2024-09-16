package com.wizeline.maven.LearningJava.config;

import com.wizeline.maven.LearningJava.model.UserDTO;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.Date;


@Configuration
public class JwtTokenConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenConfig.class);


    @Value("${jwt.secret}")
    private String secret;

    /**
     * Este método genera el token de autenticación.
     * @param userDTO Información del usuario autenticado.
     * @param claims Información adicional del usuario que se agrega al token.
     * @return Regresa el token de autenticación.
     */
    public String generateToken(UserDTO userDTO, Claims claims) {
        return Jwts.builder()
                .setSubject(userDTO.getUser())
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(5).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Validación del token utilizado durante la autenticación.
     * @param token Token de autenticación.
     * @return Regresa verdadero o falso dependiendo si es un token válido.
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT Token expirado", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token es null o vacío", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT no valido", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT no soportado", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Falló la validación de la firma");
        }
        return false;
    }
}