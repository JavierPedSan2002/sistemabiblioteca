package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import com.biblioteca.sistemabiblioteca.service.LibroService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAPA DE CONTROLADOR (API REST)
 * Esta clase actúa como el punto de entrada al sistema para todas las operaciones
 * relacionadas con la gestión de libros (RF-05 al RF-08).
 */
@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*") // Permite la conexión con el Frontend (JavaFX) evitando bloqueos de seguridad.
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService; 

    /**
     * RF-05: REGISTRO DE LIBROS
     * Recibe un objeto Libro en formato JSON y delega la persistencia a la capa de servicio.
     * Se usa @PostMapping para seguir las convenciones de creación de recursos en API REST.
     */
    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        // Se utiliza el servicio para garantizar que se apliquen reglas de negocio antes de guardar.
        return libroService.registrarLibro(libro);
    }

    /**
     * RF-07: CONSULTA DE CATÁLOGO
     * Retorna una lista completa de los libros almacenados en la base de datos MySQL.
     * Es fundamental para que el bibliotecario pueda visualizar el inventario actual.
     */
    @GetMapping
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    /**
     * RF-08: BÚSQUEDA ESPECÍFICA
     * Permite localizar un libro mediante su identificador único (ISBN).
     * Si el libro no existe, lanza una excepción controlada para evitar fallos en el sistema.
     */
    @GetMapping("/{isbn}")
    public Libro obtenerPorIsbn(@PathVariable String isbn) {
        return libroRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + isbn));
    }
}