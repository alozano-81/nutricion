package com.salud.nutricion.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.salud.nutricion.entities.UserEntitieDocument;

public interface UserRepository extends MongoRepository<UserEntitieDocument, String> {

    Optional<UserEntitieDocument> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
