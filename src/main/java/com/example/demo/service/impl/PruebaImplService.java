package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DocumentPruebaMDB;
import com.example.demo.repository.DocumentoRepository;
import com.example.demo.service.PruebaService;

@Service
public class PruebaImplService implements PruebaService {

    @Autowired
    DocumentoRepository documentoRepository;

    @Override
    public DocumentPruebaMDB getTodos(String name) {
        DocumentPruebaMDB out = new DocumentPruebaMDB();
        List<DocumentPruebaMDB> list = documentoRepository.getTodos(name);
        System.out.println("VER: " + list);
        return out;
    }

}
