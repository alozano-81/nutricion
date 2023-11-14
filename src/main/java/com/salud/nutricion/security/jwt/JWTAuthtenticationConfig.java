package com.salud.nutricion.security.jwt;

import static com.salud.nutricion.util.Constans.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JWTAuthtenticationConfig {

        public String getJWTToken(String username) {
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                                .commaSeparatedStringToAuthorityList("ROLE_USER");

                String token = Jwts
                                .builder()
                                .setId("nutricionSign")
                                .setSubject(username)
                                .claim("authorities",
                                                grantedAuthorities.stream()
                                                                .map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                .setIssuedAt(new Date(System.currentTimeMillis()))
                                .setExpiration(new Date(System.currentTimeMillis() + (1 * TOKEN_EXPIRATION_TIME)))
                                .signWith(getSigningKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512).compact();

                return "Bearer " + token;
        }

}
