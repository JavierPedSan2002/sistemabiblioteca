package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
// RF - 09
@Table(name = "prestamos")
public class Prestamo {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RF-09: Asociar un libro con un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    // RF-09: Registrar fechas de préstamo y devolución esperada
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;

    // RF-10: Registrar fecha real de devolución
    private LocalDate fechaDevolucionReal;

    // RF-11: Para listar activos, vencidos o devueltos
    private String estado; // Ejemplo: "ACTIVO", "DEVUELTO", "VENCIDO"

    // RF-10: Para el cálculo opcional de multas
    private Double multa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

}