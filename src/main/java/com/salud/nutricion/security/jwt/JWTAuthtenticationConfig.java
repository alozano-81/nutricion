package com.salud.nutricion.security.jwt;

import static com.salud.nutricion.util.Constans.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JWTAuthtenticationConfig {

        @Value("${nutricion.add.jwtSecret}")
        private String jwtSecret;

        @Value("${nutricion.add.firmaJwt}")
        private String firmaJwt;

        @Value("${nutricion.add.authorities}")
        private String authorities;

        @Value("${nutricion.add.tiempoTTLsesion}")
        private int ttl;

        public String getJWTToken(String username, String rolAuth) {
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                                .commaSeparatedStringToAuthorityList(rolAuth);
                String token = Jwts
                                .builder()
                                .setId(firmaJwt)
                                .setSubject(username)
                                .claim(authorities,
                                                grantedAuthorities.stream()
                                                                .map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                .setIssuedAt(new Date(System.currentTimeMillis()))
                                .setExpiration(new Date(System.currentTimeMillis() + (ttl * TOKEN_EXPIRATION_TIME)))
                                .signWith(getSigningKey(jwtSecret), SignatureAlgorithm.HS512).compact();
                return TOKEN_BEARER_PREFIX + token;
        }

}
