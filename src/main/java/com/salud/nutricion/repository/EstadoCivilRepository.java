package com.salud.nutricion.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.salud.nutricion.entities.EstadosCivilEntitieDocument;

@Repository
public interface EstadoCivilRepository extends MongoRepository<EstadosCivilEntitieDocument, String> {

    @Query(value = "{}")
    List<EstadosCivilEntitieDocument> getTodos();

}
