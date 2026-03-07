package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * ENTIDAD USUARIOS: ACTUALIZADA PARA biblioteca_universidad
 * Ajustada para coincidir con el script SQL de Ricardo.
 */
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // CAMBIO: Antes decía "id"
    private Integer id; // CAMBIO: Antes era Long, ahora es Integer para el SQL

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String correoElectronico;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash; 

    @Column(name = "id_rol")
    private Integer idRol; 

    @Column(name = "fecha_registro", updatable = false, insertable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "estado")
    private Boolean estado; 

    // --- MÉTODOS DE ACCESO ACTUALIZADOS ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}