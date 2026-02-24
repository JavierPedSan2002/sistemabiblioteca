package com.biblioteca.sistemabiblioteca.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @Column(name = "id_libro", length = 20)
    private String isbn; // El ISBN ahora es el ID principal según el SQL

    private String titulo;
    private String autor;
    private String editorial;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @Column(name = "categoria")
    private String categoria; // Cambiado de 'genero' a 'categoria' para igualar el SQL

    @Column(name = "copias_disponibles")
    private int copiasDisponibles;

    @Column(name = "ubicacion_estanteria")
    private String ubicacionEstanteria;

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

    // Actualiza tus Getters y Setters para estos nuevos nombres
}