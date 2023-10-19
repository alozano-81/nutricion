package com.salud.nutricion;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({ "com.salud.nutricion.service" })
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "com.salud.nutricion.repository" })
public class NutricionApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutricionApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
