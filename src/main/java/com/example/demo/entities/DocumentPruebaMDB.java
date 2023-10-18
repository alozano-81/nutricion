package com.example.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("prueba")
public class DocumentPruebaMDB {

    @Id
    private String id;

    private String name;
    // private Long cantidad;

}
