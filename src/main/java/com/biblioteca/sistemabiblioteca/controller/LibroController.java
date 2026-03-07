package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import com.biblioteca.sistemabiblioteca.service.LibroService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAPA DE CONTROLADOR (API REST) - VERSIÓN FINAL ACTUALIZADA
 * Gestión completa de libros: Registro, Consulta, Eliminación y Actualización.
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
     */
    @GetMapping("/{idLibro}")
    public Libro obtenerPorId(@PathVariable String idLibro) {
        return libroRepository.findById(idLibro)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + idLibro));
    }

    /**
     * RF-09: ACTUALIZACIÓN DE INFORMACIÓN (NUEVO)
     * Permite modificar cualquier campo del libro usando su ID.
     */
    @PutMapping("/{idLibro}")
    public Libro actualizarLibro(@PathVariable String idLibro, @RequestBody Libro datosNuevos) {
        return libroRepository.findById(idLibro)
            .map(libroExistente -> {
                libroExistente.setTitulo(datosNuevos.getTitulo());
                libroExistente.setAutor(datosNuevos.getAutor());
                libroExistente.setEditorial(datosNuevos.getEditorial());
                libroExistente.setAnioPublicacion(datosNuevos.getAnioPublicacion());
                libroExistente.setCategoria(datosNuevos.getCategoria());
                libroExistente.setCopiasDisponibles(datosNuevos.getCopiasDisponibles());
                libroExistente.setUbicacionEstanteria(datosNuevos.getUbicacionEstanteria());
                return libroRepository.save(libroExistente);
            })
            .orElseThrow(() -> new RuntimeException("No se pudo actualizar. Libro no encontrado con ID: " + idLibro));
    }

    /**
     * RF-06: ELIMINACIÓN DE LIBROS
     */
    @DeleteMapping("/{idLibro}")
    public void eliminarLibro(@PathVariable String idLibro) {
        libroRepository.deleteById(idLibro);
    }
}