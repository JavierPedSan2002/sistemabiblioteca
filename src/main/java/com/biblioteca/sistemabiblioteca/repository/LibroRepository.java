package com.biblioteca.sistemabiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.sistemabiblioteca.model.Libro;

/**
 * CAPA DE REPOSITORIO: INTERFAZ DE PERSISTENCIA PARA LIBROS
 * Esta interfaz hereda de JpaRepository, proporcionando todos los métodos CRUD 
 * necesarios (Crear, Leer, Actualizar, Borrar) sin necesidad de implementar SQL manual.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, String> { 
    
    /**
     * CONFIGURACIÓN DE LLAVE PRIMARIA:
     * Se define el segundo parámetro como 'String' debido a que el modelo 'Libro' 
     * utiliza el ISBN como identificador único y llave primaria (@Id).
     */
}