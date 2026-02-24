package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Libro registrarLibro(Libro libro) {
        // Aquí podrías validar si el ISBN ya existe antes de guardar
        return libroRepository.save(libro);
    }
}