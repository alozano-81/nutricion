package com.salud.nutricion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.salud.nutricion.entities.LoginEntitieDocument;

public interface LoginRepository extends MongoRepository<LoginEntitieDocument, String> {

    @Query("{user:'?0',password:'?1'}")
    LoginEntitieDocument getLogin(String usuario, String password);

    @Query("{user:'?0'}")
    LoginEntitieDocument getPassEncode(String usuario);
}
