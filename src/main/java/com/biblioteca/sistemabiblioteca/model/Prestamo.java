package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ENTIDAD PRESTAMO: EL NÚCLEO TRANSACCIONAL
 * Esta clase vincula a los Usuarios con los Libros, representando el proceso 
 * principal de la biblioteca (RF-09 al RF-11).
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Long id;

    /**
     * @ManyToOne: Relación muchos a uno. 
     * Muchos préstamos pueden referenciar al mismo Libro mediante su ISBN.
     */
    @ManyToOne
    @JoinColumn(name = "isbn") 
    private Libro libro;

    /**
     * @ManyToOne: Relación con la tabla Usuarios. 
     * Identifica al socio (alumno/docente) que solicita el material.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario") 
    private Usuarios usuario;

    /**
     * RF-09: AUDITORÍA DE ENTREGA
     * Relación que identifica al bibliotecario responsable de procesar la salida.
     */
    @ManyToOne
    @JoinColumn(name = "id_bibliotecario_entrega") 
    private Usuarios bibliotecario;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaPrestamo; 

    /**
     * RF-09: PLAZO DE ENTREGA
     * Define la fecha límite para evitar sanciones (normalmente entre 7 y 15 días).
     */
    @Column(name = "fecha_devolucion_esperada")
    private LocalDate fechaDevolucionEsperada; 

    /**
     * RF-10: CONTROL DE DEVOLUCIÓN
     * Almacena el momento exacto en que el libro regresa físicamente.
     */
    @Column(name = "fecha_devolucion_real")
    private LocalDateTime fechaDevolucionReal; 

    @Column(name = "monto_multa_generado")
    private Double multa;

    /**
     * ESTADO DEL PRÉSTAMO
     * Gestiona el ciclo de vida: 'Activo', 'Devuelto' o 'Moroso'.
     */
    @Column(name = "estado_prestamo")
    private String estado;

    // --- MÉTODOS DE ACCESO (GETTERS Y SETTERS) ---
    // Proporcionan la interfaz necesaria para que el Service y el Controller manipulen los datos.

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Double getMulta() { return multa; }
    public void setMulta(Double multa) { this.multa = multa; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}