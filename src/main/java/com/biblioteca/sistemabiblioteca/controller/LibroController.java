package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import com.biblioteca.sistemabiblioteca.service.LibroService; // Importación añadida
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService; // CONEXIÓN: Inyectamos el servicio

    // RF-05: Registrar nuevo libro usando el Service
    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        // Usamos el service para centralizar la lógica de guardado
        return libroService.registrarLibro(libro);
    }

    // RF-07: Listar todos los libros (Consulta de catálogo)
    @GetMapping
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    // RF-08: Buscar un libro específico por ISBN
    @GetMapping("/{isbn}")
    public Libro obtenerPorIsbn(@PathVariable String isbn) {
        return libroRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + isbn));
    }
}