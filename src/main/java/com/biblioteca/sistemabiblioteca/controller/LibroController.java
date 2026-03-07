package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import com.biblioteca.sistemabiblioteca.service.LibroService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAPA DE CONTROLADOR (API REST) - ACTUALIZADO PARA biblioteca_universidad
 * Punto de entrada para la gestión de libros (RF-05 al RF-08).
 */
@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*") 
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService; 

    /**
     * RF-05: REGISTRO DE LIBROS
     * Ahora utiliza el modelo alineado con la tabla 'libros'.
     */
    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroService.registrarLibro(libro);
    }

    /**
     * RF-07: CONSULTA DE CATÁLOGO
     */
    @GetMapping
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    /**
     * RF-08: BÚSQUEDA ESPECÍFICA POR ID_LIBRO
     * Se actualiza el parámetro para coincidir con la llave primaria del nuevo script.
     */
    @GetMapping("/{idLibro}")
    public Libro obtenerPorId(@PathVariable String idLibro) {
        return libroRepository.findById(idLibro)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + idLibro));
    }
}