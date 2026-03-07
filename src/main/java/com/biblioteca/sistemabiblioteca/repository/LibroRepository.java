package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * CAPA DE REPOSITORIO: INTERFAZ DE PERSISTENCIA PARA LIBROS
 * Actualizada para coincidir con el campo 'idLibro'.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, String> { 

    /**
     * RF-06: BÚSQUEDA POR TÍTULO
     */
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * RF-06: BÚSQUEDA POR AUTOR
     */
    List<Libro> findByAutorContainingIgnoreCase(String autor);

    /**
     * RF-07: FILTRADO POR CATEGORÍA
     */
    List<Libro> findByCategoria(String categoria);

    /**
     * VALIDACIÓN DE EXISTENCIA
     * CAMBIO: Se actualiza de existsByIsbn a existsByIdLibro para que coincida 
     * con el nombre de la variable en el Modelo Libro.
     */
    boolean existsByIdLibro(String idLibro);
}