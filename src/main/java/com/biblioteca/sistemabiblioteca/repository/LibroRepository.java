package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * CAPA DE REPOSITORIO: INTERFAZ DE PERSISTENCIA PARA LIBROS
 * Proporciona acceso a la base de datos MySQL mediante Spring Data JPA.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, String> { 

    /**
     * RF-06: BÚSQUEDA POR TÍTULO
     * Permite encontrar libros aunque el usuario no escriba el nombre completo (Like).
     */
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * RF-06: BÚSQUEDA POR AUTOR
     * Filtra el catálogo por el nombre del escritor.
     */
    List<Libro> findByAutorContainingIgnoreCase(String autor);

    /**
     * RF-07: FILTRADO POR CATEGORÍA
     * Útil para agrupar libros del mismo género (Novela, Ciencia, etc.).
     */
    List<Libro> findByCategoria(String categoria);

    /**
     * VALIDACIÓN DE EXISTENCIA
     * Método interno para verificar si un ISBN ya está registrado.
     */
    boolean existsByIsbn(String isbn);
}