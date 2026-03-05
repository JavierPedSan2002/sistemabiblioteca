package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * REPOSITORIO DE GESTIÓN DE USUARIOS
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    /**
     * RF-01: VALIDACIÓN DE IDENTIDAD ÚNICA
     * Vital para el proceso de registro y login.
     */
    Optional<Usuarios> findByCorreoElectronico(String correoElectronico);

    /**
     * RF-02: BÚSQUEDA POR NOMBRE (MEJORADA)
     * El 'Containing' permite buscar coincidencias parciales (ej: busca "Juan" y encuentra "Juan Perez").
     * El 'IgnoreCase' ignora mayúsculas/minúsculas.
     */
    List<Usuarios> findByNombreContainingIgnoreCase(String nombre);

    /**
     * RF-03: FILTRADO POR ESTADO
     * Ideal para el método listarActivos() del Service.
     */
    List<Usuarios> findByEstado(Boolean estado);

    /**
     * RF-04: VERIFICACIÓN DE EXISTENCIA
     * Útil para validaciones rápidas antes de operaciones pesadas.
     */
    boolean existsByCorreoElectronico(String correoElectronico);

    /**
     * GESTIÓN DE PRIVILEGIOS POR ROL
     * Nota: Asegúrate de que en tu clase Usuarios el campo se llame idRol.
     */
    List<Usuarios> findByIdRol(Integer idRol);
}