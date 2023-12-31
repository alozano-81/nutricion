package com.salud.nutricion.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.salud.nutricion.entities.DocumentRegistroPacientes;

@Repository
public interface RegistroPacientesRepository extends MongoRepository<DocumentRegistroPacientes, String> {

    @Query("{nombres:'?0'}")
    List<DocumentRegistroPacientes> getByName(String name);

    @Query(value = "{}")
    List<DocumentRegistroPacientes> getTodos();

    @Query("{documento: ?0}")
    DocumentRegistroPacientes getById(Long id);

}
