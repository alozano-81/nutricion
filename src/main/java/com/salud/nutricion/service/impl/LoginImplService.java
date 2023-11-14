package com.salud.nutricion.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.salud.nutricion.dto.LoginDTO;
import com.salud.nutricion.entities.LoginEntitieDocument;
import com.salud.nutricion.repository.LoginRepository;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.security.jwt.JWTAuthtenticationConfig;
import com.salud.nutricion.service.LoginService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class LoginImplService implements LoginService {

    @Autowired
    JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LoginRepository loginRepository;

    @Override
    public Respuesta validarCredenciales(String usuario, String password) {
        Respuesta out = new Respuesta();

        try {
            LoginEntitieDocument obj = loginRepository.getLogin(usuario, password);
            if (obj != null) {
                LoginDTO objDto = modelMapper.map(obj, LoginDTO.class);
                // out.setObj(objDto);
                String token = jwtAuthtenticationConfig.getJWTToken(usuario);
                out.setToken(token);
                out.setStatus(HttpStatus.ACCEPTED);
            } else {
                out.setStatus(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println("VER MSN ERROR: " + e.getMessage());
            out.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return out;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                // .signWith(SignatureAlgorithm.HS512,
                // Base64.getEncoder().encodeToString("bad-key".getBytes(US_ASCII)))

                .compact();

        return "Bearer " + token;
    }

    private Key getSigningKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // or HS384 or HS512
        // byte[] keyBytes = DatatypeConverter.parseBase64Binary("123asdfghjk");
        byte[] bytes = java.util.Base64.getDecoder().decode("mySecretKey");
        // return Keys.hmacShaKeyFor(bytes);
        return key;
    }

}
