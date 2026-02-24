package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Usuarios bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public java.time.LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(java.time.LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public java.time.LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(java.time.LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public java.time.LocalDateTime getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(java.time.LocalDateTime fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "isbn") // Se une por el ISBN del libro según el SQL
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "id_usuario") // Usuario que recibe el libro
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_bibliotecario_entrega") // Cumple RF-09: Quién registra el préstamo
    private Usuarios bibliotecario;

    @Column(name = "fecha_salida")
    private java.time.LocalDateTime fechaPrestamo; // Automática según SQL

    @Column(name = "fecha_devolucion_esperada")
    private java.time.LocalDate fechaDevolucionEsperada; // RF-09: 7-15 días

    @Column(name = "fecha_devolucion_real")
    private java.time.LocalDateTime fechaDevolucionReal; // RF-10: Registro real

    @Column(name = "monto_multa_generado")
    private Double multa;

    @Column(name = "estado_prestamo")
    private String estado; // 'Activo', 'Devuelto', 'En Moroso' según SQL
}