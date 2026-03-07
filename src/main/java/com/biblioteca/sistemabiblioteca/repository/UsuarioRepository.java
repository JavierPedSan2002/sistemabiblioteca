package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> { // CAMBIO: Long -> Integer

    /**
     * RF-01: BUSQUEDA POR CREDENCIALES
     * Fundamental para el proceso de Login.
     */
    Optional<Usuarios> findByCorreoElectronico(String correoElectronico);

    /**
     * RF-02: BÚSQUEDA POR NOMBRE COMPLETO
     * Permite localizar usuarios para la gestión administrativa.
     */
    List<Usuarios> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    /**
     * RF-04: FILTRADO POR ESTADO
     * Para listar solo usuarios activos o inactivos.
     */
    List<Usuarios> findByEstado(Boolean estado);

    /**
     * VALIDACIÓN DE UNICIDAD
     * Evita duplicados durante el registro (RF-01).
     */
    boolean existsByCorreoElectronico(String correoElectronico);

    /**
     * FILTRADO POR ROL
     * Útil para auditoría: ver quiénes son Bibliotecarios, Estudiantes, etc.
     */
    List<Usuarios> findByIdRol(Integer idRol);
}