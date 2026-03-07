package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * CAPA DE MODELO: ENTIDAD LIBRO - ACTUALIZADA
 * Alineada con la tabla 'libros' de la base de datos de Ricardo.
 */
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @Column(name = "id_libro", length = 20)
    private String idLibro; // CAMBIO: Se renombra de 'isbn' a 'idLibro' para ser consistentes

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false) // Ricardo lo puso como NOT NULL en el script
    private String autor;

    private String editorial;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion; // Cambiado a Integer para permitir nulos si es necesario

    @Column(name = "categoria")
    private String categoria; 

    @Column(name = "copias_disponibles", nullable = false)
    private int copiasDisponibles;

    @Column(name = "ubicacion_estanteria")
    private String ubicacionEstanteria;

    // --- MÉTODOS DE ACCESO ACTUALIZADOS ---

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
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

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
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