package com.salud.nutricion.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.salud.nutricion.entities.ERole;
import com.salud.nutricion.entities.RoleEntitieDocument;

public interface RoleRepository extends MongoRepository<RoleEntitieDocument, String> {

    Optional<RoleEntitieDocument> findByNombre(ERole nombre);
}