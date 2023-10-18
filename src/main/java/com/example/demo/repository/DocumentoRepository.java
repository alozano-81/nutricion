package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.DocumentPruebaMDB;

@Repository
public interface DocumentoRepository extends MongoRepository<DocumentPruebaMDB, String> {

    @Query("{name:'?0'}")
    List<DocumentPruebaMDB> getTodos(String name);

}
