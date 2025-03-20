package com.salud.nutricion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.salud.nutricion.entities.RegistroInfoPacientesDocument;

@Repository
public interface RegistroInfoPacientesRepository extends MongoRepository<RegistroInfoPacientesDocument, String> {

    @Query("{nombres:'?0'}")
    List<RegistroInfoPacientesDocument> getByName(String name);

    @Query(value = "{}")
    List<RegistroInfoPacientesDocument> getTodos();

    @Query("{documento: ?0}")
    Optional<RegistroInfoPacientesDocument> getById(Long doc);

    @Query("{documento: ?0, id: ?1}")
    Optional<RegistroInfoPacientesDocument> getById(Long doc, String id);

}
