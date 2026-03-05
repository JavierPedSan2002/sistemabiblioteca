package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CAPA DE SERVICIO: GESTIÓN DE INVENTARIO
 * Centraliza las reglas de negocio para el catálogo de libros.
 */
@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    /**
     * RF-05: REGISTRO DE LIBROS CON VALIDACIÓN DE ISBN
     * Corrige el error de comparación null para tipos primitivos int.
     */
    @Transactional
    public Libro registrarLibro(Libro libro) {
        // 1. Validación de existencia
        if (libroRepository.existsById(libro.getIsbn())) {
            throw new RuntimeException("ERROR RF-05: Ya existe un libro registrado con el ISBN: " + libro.getIsbn());
        }

        // 2. Validación de consistencia
        // Eliminamos la comparación == null porque copiasDisponibles es un 'int'
        if (libro.getCopiasDisponibles() < 0) {
            libro.setCopiasDisponibles(0); // Asegura que no haya stock negativo al registrar
        }

        // 3. Persistencia
        return libroRepository.save(libro);
    }

    /**
     * RF-08: ACTUALIZACIÓN DE DATOS DEL LIBRO
     */
    @Transactional
    public Libro actualizarLibro(String isbn, Libro libroActualizado) {
        return libroRepository.findById(isbn)
            .map(libro -> {
                libro.setTitulo(libroActualizado.getTitulo());
                libro.setAutor(libroActualizado.getAutor());
                libro.setUbicacionEstanteria(libroActualizado.getUbicacionEstanteria());
                
                // Validación extra al actualizar stock
                if (libroActualizado.getCopiasDisponibles() < 0) {
                    throw new RuntimeException("No se puede asignar un stock negativo.");
                }
                
                libro.setCopiasDisponibles(libroActualizado.getCopiasDisponibles());
                return libroRepository.save(libro);
            })
            .orElseThrow(() -> new RuntimeException("Libro no encontrado para actualizar con ISBN: " + isbn));
    }
}