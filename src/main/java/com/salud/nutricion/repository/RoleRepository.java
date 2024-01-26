package com.salud.nutricion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.salud.nutricion.entities.ERole;
import com.salud.nutricion.entities.RoleEntitieDocument;

public interface RoleRepository extends MongoRepository<RoleEntitieDocument, String> {
    List<RoleEntitieDocument> findAll();

    Optional<RoleEntitieDocument> findByNombre(ERole nombre);
}