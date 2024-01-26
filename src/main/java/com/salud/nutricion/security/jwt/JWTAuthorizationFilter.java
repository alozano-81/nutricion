package com.salud.nutricion.security.jwt;

import static com.salud.nutricion.util.Constans.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.salud.nutricion.respuestas.Respuesta;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Value("${nutricion.add.jwtSecret}")
    private String jwtSecret;

    @Value("${nutricion.add.authorities}")
    private String authorities;

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private Claims setSigningKey(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER_AUTHORIZACION_KEY).replace(TOKEN_BEARER_PREFIX, "");
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(jwtSecret))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private void setAuthentication(Claims claims) {
        List<String> authorities = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean isJWTValid(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER_AUTHORIZACION_KEY);
        if (authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_BEARER_PREFIX))
            return false;
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isJWTValid(request, response)) {
                Claims claims = setSigningKey(request);
                if (claims.get(authorities) != null) {
                    setAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            logger.error("Invalid JWT token invalido: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            // response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            // return;
        }
    }

    /**
     * Valida el token en sesion
     * 
     * @param token
     * @return
     * @throws ExpiredJwtException
     * @throws UnsupportedJwtException
     * @throws IllegalArgumentException
     */
    public Respuesta extractClaims(String token)
            throws ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        Respuesta out = new Respuesta();
        Claims claims = null;
        try {
            // Parsear el token y obtener los claims
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token.trim())
                    .getBody();
            out.setStatus(HttpStatus.ACCEPTED);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            out.setStatus(HttpStatus.CONFLICT);
            out.setMsn("Invalid JWT token:" + e.getMessage());
        } catch (SignatureException e) {
            // Excepción lanzada si la firma del token no es válida
            logger.error("Error de firma del token:", e.getMessage());
            out.setStatus(HttpStatus.CONFLICT);
            out.setMsn("Error de firma del token:" + e.getMessage());
        } catch (Exception e) {
            // Otras excepciones, por ejemplo, si el token está mal formado
            logger.error("Error al parsear el token: ", e.getMessage());
            out.setStatus(HttpStatus.CONFLICT);
            out.setMsn(e.getMessage());
        }
        out.setClains(claims);
        return out;
        // Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public Respuesta procesarToken(String jwtToken) {
        Respuesta out = new Respuesta();
        out = extractClaims(jwtToken);
        // Extraer valores específicos del token
        // String username = jwtUtil.extractUsername(jwtToken);
        // List<String> roles = claims.get("ROLE_USER", List.class); // Suponiendo que
        // "roles" es un claim personalizado
        return out;
    }

    /*
     * public String extractUsername(String token) {
     * return extractClaims(token).getSubject();
     * }
     */

}
