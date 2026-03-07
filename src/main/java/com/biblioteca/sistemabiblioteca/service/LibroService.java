package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CAPA DE SERVICIO: GESTIÓN DE INVENTARIO - ACTUALIZADO
 * Centraliza las reglas de negocio para el catálogo de libros.
 */
@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    /**
     * RF-05: REGISTRO DE LIBROS CON VALIDACIÓN DE ID_LIBRO
     */
    @Transactional
    public Libro registrarLibro(Libro libro) {
        // 1. Validación de existencia usando el nuevo nombre de campo idLibro
        if (libroRepository.existsById(libro.getIdLibro())) {
            throw new RuntimeException("ERROR RF-05: Ya existe un libro registrado con el ID: " + libro.getIdLibro());
        }

        // 2. Validación de consistencia de stock
        if (libro.getCopiasDisponibles() < 0) {
            libro.setCopiasDisponibles(0); 
        }

        // 3. Persistencia
        return libroRepository.save(libro);
    }

    /**
     * RF-08: ACTUALIZACIÓN DE DATOS DEL LIBRO
     * Se cambia el parámetro 'isbn' por 'idLibro' para ser consistentes con la BD.
     */
    @Transactional
    public Libro actualizarLibro(String idLibro, Libro libroActualizado) {
        return libroRepository.findById(idLibro)
            .map(libro -> {
                libro.setTitulo(libroActualizado.getTitulo());
                libro.setAutor(libroActualizado.getAutor());
                libro.setEditorial(libroActualizado.getEditorial()); // Agregado para integridad
                libro.setAnioPublicacion(libroActualizado.getAnioPublicacion()); // Agregado para integridad
                libro.setCategoria(libroActualizado.getCategoria()); // Agregado para integridad
                libro.setUbicacionEstanteria(libroActualizado.getUbicacionEstanteria());
                
                // Validación de stock
                if (libroActualizado.getCopiasDisponibles() < 0) {
                    throw new RuntimeException("No se puede asignar un stock negativo.");
                }
                
                libro.setCopiasDisponibles(libroActualizado.getCopiasDisponibles());
                return libroRepository.save(libro);
            })
            .orElseThrow(() -> new RuntimeException("Libro no encontrado para actualizar con ID: " + idLibro));
    }
}