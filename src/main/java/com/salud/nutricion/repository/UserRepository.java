package com.salud.nutricion.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.salud.nutricion.entities.UserEntitieDocument;

public interface UserRepository extends MongoRepository<UserEntitieDocument, String> {

    Optional<UserEntitieDocument> findByUsername(String username);

    Optional<UserEntitieDocument> findByUsernameAndPassword(String username, String password);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsernameAndPassword(String username, String password);

    @Query("{username:'?0',password:'?1'}")
    UserEntitieDocument getLogin(String usuario, String password);
}