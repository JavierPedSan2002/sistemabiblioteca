package com.biblioteca.frontend.models;

public class Libro {

    private String idLibro;

    private String titulo;
    private String autor;
    private String editorial;

    private Integer anioPublicacion;

    private String categoria;

    private Integer copiasDisponibles;

    private String ubicacionEstanteria;

    public Libro() {}

    public String getIdLibro() { return idLibro; }
    public void setIdLibro(String idLibro) { this.idLibro = idLibro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Integer getCopiasDisponibles() { return copiasDisponibles; }
    public void setCopiasDisponibles(Integer copiasDisponibles) { this.copiasDisponibles = copiasDisponibles; }

    public String getUbicacionEstanteria() { return ubicacionEstanteria; }
    public void setUbicacionEstanteria(String ubicacionEstanteria) { this.ubicacionEstanteria = ubicacionEstanteria; }

}