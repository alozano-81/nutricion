package com.salud.nutricion;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({ "com.salud.nutricion.service", "com.salud.nutricion.security.jwt" })
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "com.salud.nutricion.repository" })
@EnableConfigurationProperties
public class NutricionApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutricionApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")//
						.allowedOrigins("*")//
						.allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")//
						.allowedHeaders("*");
			}
		};
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
