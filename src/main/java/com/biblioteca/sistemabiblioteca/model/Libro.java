package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * CAPA DE MODELO: ENTIDAD LIBRO
 * Esta clase representa la tabla 'libros' en la base de datos. 
 * Es un POJO (Plain Old Java Object) que utiliza JPA para el mapeo objeto-relacional (ORM).
 */
@Entity
@Table(name = "libros")
public class Libro {

    /**
     * @Id: Define el ISBN como la llave primaria natural de la tabla.
     * Se ha configurado con una longitud de 20 caracteres para cumplir con los estándares internacionales de ISBN-10 y ISBN-13.
     */
    @Id
    @Column(name = "id_libro", length = 20)
    private String isbn; 

    @Column(nullable = false)
    private String titulo;

    private String autor;
    private String editorial;

    /**
     * @Column: Sincroniza el nombre del atributo en Java con la columna física en MySQL.
     */
    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @Column(name = "categoria")
    private String categoria; 

    @Column(name = "copias_disponibles")
    private int copiasDisponibles;

    @Column(name = "ubicacion_estanteria")
    private String ubicacionEstanteria;

    // --- MÉTODOS DE ACCESO (GETTERS Y SETTERS) ---
    // Permiten la encapsulación de los datos y su correcta manipulación por los frameworks.

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    public String getUbicacionEstanteria() {
        return ubicacionEstanteria;
    }

    public void setUbicacionEstanteria(String ubicacionEstanteria) {
        this.ubicacionEstanteria = ubicacionEstanteria;
    }
}