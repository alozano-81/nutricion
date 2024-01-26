package com.salud.nutricion.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig {

        @Value("${nutricion.add.rutaauth}")
        private String rutaauth;

        @Value("${nutricion.add.rutasinauth}")
        private String rutasinauth;

        @Autowired
        JWTAuthorizationFilter jwtAuthorizationFilter;

        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf
                                                .disable())
                                // .authorizeHttpRequests(authz -> authz
                                // .requestMatchers(HttpMethod.POST, Constans.LOGIN_URL).permitAll()
                                // .anyRequest().authenticated())
                                .authorizeHttpRequests(
                                                auth -> auth.requestMatchers(rutaauth).permitAll()
                                                                .requestMatchers(rutasinauth)
                                                                .permitAll().anyRequest().authenticated())
                                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}