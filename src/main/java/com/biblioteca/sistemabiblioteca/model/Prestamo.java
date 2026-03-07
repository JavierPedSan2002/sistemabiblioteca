package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * ENTIDAD PRESTAMO: ACTUALIZADA PARA biblioteca_universidad
 * Vincula Usuarios con Libros usando la estructura de Ricardo.
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer id; // Cambiado de Long a Integer para coincidir con el INT de MySQL

    @ManyToOne
    @JoinColumn(name = "id_libro") // Cambiado de 'isbn' a 'id_libro'
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "id_usuario") 
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_bibliotecario_entrega") 
    private Usuarios bibliotecario;

    @Column(name = "fecha_salida", insertable = false, updatable = false)
    private LocalDateTime fechaPrestamo; 

    @Column(name = "fecha_devolucion_esperada")
    private LocalDate fechaDevolucionEsperada; 

    @Column(name = "fecha_devolucion_real")
    private LocalDateTime fechaDevolucionReal; 

    @Column(name = "monto_multa_generado")
    private BigDecimal multa; // BigDecimal es mejor para dinero (monto_multa en SQL)

    @Column(name = "estado_prestamo")
    private String estadoPrestamo; // Cambiado de 'estado' a 'estado_prestamo'

    // --- MÉTODOS DE ACCESO ACTUALIZADOS ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }

    public Usuarios getBibliotecario() { return bibliotecario; }
    public void setBibliotecario(Usuarios bibliotecario) { this.bibliotecario = bibliotecario; }

    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucionEsperada() { return fechaDevolucionEsperada; }
    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) { this.fechaDevolucionEsperada = fechaDevolucionEsperada; }

    public LocalDateTime getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDateTime fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }

    public BigDecimal getMulta() { return multa; }
    public void setMulta(BigDecimal multa) { this.multa = multa; }

    // Este es el método que tu controlador buscaba y no encontraba:
    public String getEstadoPrestamo() { return estadoPrestamo; }
    public void setEstadoPrestamo(String estadoPrestamo) { this.estadoPrestamo = estadoPrestamo; }
}