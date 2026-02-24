package com.biblioteca.sistemabiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.sistemabiblioteca.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> { 
    // Cambiamos Long por String porque el ISBN es el nuevo ID
}