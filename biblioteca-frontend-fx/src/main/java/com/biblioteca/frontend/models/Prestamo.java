package com.biblioteca.frontend.models;

public class Prestamo {

    private Integer id;
    private Libro libro;
    private Usuario usuario;

    private String fechaPrestamo;
    private String fechaDevolucionEsperada;
    private String fechaDevolucionReal;

    private String estadoPrestamo;

    public Integer getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public String getTituloLibro() {
        return libro != null ? libro.getTitulo() : "";
    }

    public String getNombreUsuario() {
        return usuario != null ? usuario.getNombreCompleto() : "";
    }
}