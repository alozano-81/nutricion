package com.salud.nutricion.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.salud.nutricion.entities.PaisEntitieDocument;

@Repository
public interface PaisesRepository extends MongoRepository<PaisEntitieDocument, String> {

    @Query(value = "{}")
    List<PaisEntitieDocument> getTodos();

}
