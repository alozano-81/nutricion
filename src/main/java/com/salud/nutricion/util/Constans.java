package com.salud.nutricion.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class Constans {

    // Spring Security
    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    // public static final String ISSUER_INFO = "https://www.autentia.com/";
    // public static final String SUPER_SECRET_KEY =
    // "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXJjb21vY2xhdmVlbnVucHJvamVjdG9kZWVtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFiYXNlNjQ=";
    // algoritmo debe ser >= 512 bits
    public static final String SUPER_SECRET_KEY = "430899ec3977e3f7522ec639f36a324d1ed850fc==430899ec3977e3f7522ec639f36a324d1ed850fc";
    public static final long TOKEN_EXPIRATION_TIME = 60000; // 10 day

    public static Key getSigningKeyB64(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
