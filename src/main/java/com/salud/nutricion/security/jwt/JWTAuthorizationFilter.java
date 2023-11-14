package com.salud.nutricion.security.jwt;

import static com.salud.nutricion.util.Constans.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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

    private Claims setSigningKey(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER_AUTHORIZACION_KEY).replace(TOKEN_BEARER_PREFIX, "");

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SUPER_SECRET_KEY))
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
                if (claims.get("authorities") != null) {
                    setAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    public Claims extractClaims(String token) {

        try {
            // Parsear el token y obtener los claims
            Claims claims = Jwts.parser()
                    .setSigningKey(SUPER_SECRET_KEY)
                    .parseClaimsJws(token.trim())
                    .getBody();

            // Obtener los valores del token
            String username = claims.getSubject();
            String userId = claims.get("userId", String.class);
            // Puedes agregar más según los claims que hayas incluido en el token

            // Imprimir los valores
            System.out.println("Username: " + username);
            System.out.println("UserID: " + userId);
        } catch (SignatureException e) {
            // Excepción lanzada si la firma del token no es válida
            System.err.println("Error de firma del token: " + e.getMessage());
        } catch (Exception e) {
            // Otras excepciones, por ejemplo, si el token está mal formado
            System.err.println("Error al parsear el token: " + e.getMessage());
        }
        return Jwts.parser().setSigningKey(SUPER_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

}
