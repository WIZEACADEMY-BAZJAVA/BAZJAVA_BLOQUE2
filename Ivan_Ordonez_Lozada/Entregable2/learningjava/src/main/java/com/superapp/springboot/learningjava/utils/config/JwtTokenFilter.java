package com.superapp.springboot.learningjava.utils.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.superapp.springboot.learningjava.utils.exceptions.ExcepcionUnica;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenConfig jwtTokenConfig;

  @Value("${jwt.secret}")
  private String secret;

  private final String HEADER = "Authorization";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (jwtExists(request)) {
      String token = getAccessToken(request);

      if (jwtTokenConfig.validateAccessToken(token)) {
        Claims claims = validateToken(token);
        setUpSpringAuthentication(claims);
      } else {
        throw new ExcepcionUnica(404, Collections.singletonList("Token no valido"));
      }
    }
    filterChain.doFilter(request, response);
  }

  private String getAccessToken(HttpServletRequest request) {
    String header = request.getHeader(HEADER);
    String token = header.split(" ")[1].trim();
    return token;
  }

  private Claims validateToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private boolean jwtExists(HttpServletRequest request) {
    String authenticationHeader = request.getHeader(HEADER);
    if (authenticationHeader == null) {
      return false;
    }
    return true;
  }

  private void setUpSpringAuthentication(Claims claims) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList(claims.get("authorities").toString());

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
        grantedAuthorities);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}
