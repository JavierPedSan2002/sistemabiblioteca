package com.biblioteca.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * ENTIDAD USUARIOS: GESTIÓN DE CUENTAS Y ACCESO
 * Define la estructura de los perfiles que interactúan con la biblioteca,
 * cumpliendo con los requerimientos de registro y seguridad (RF-01 al RF-04).
 */
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    /**
     * @Column(unique = true): Garantiza que no existan dos cuentas con el mismo correo,
     * cumpliendo con la restricción de integridad del RF-01.
     */
    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String correoElectronico;

    /**
     * RF-01 SEGURIDAD: Almacenamos el hash de la contraseña, no el texto plano,
     * siguiendo las mejores prácticas de ciberseguridad industrial.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash; 

    /**
     * GESTIÓN DE ROLES: Vincula al usuario con su nivel de permiso.
     * 1: Estudiante, 2: Profesor, 3: Bibliotecario (RF-01).
     */
    @Column(name = "id_rol")
    private Integer idRol; 

    /**
     * @Column(updatable = false): Registra el momento exacto de la creación
     * y protege este dato para que no sea modificado posteriormente.
     */
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    /**
     * ELIMINACIÓN LÓGICA: TRUE representa una cuenta habilitada,
     * FALSE representa una cuenta desactivada (RF-04).
     */
    private Boolean estado; 

    // --- MÉTODOS DE ACCESO (GETTERS Y SETTERS) ---
    // Estándares de encapsulamiento para la comunicación entre capas.

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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