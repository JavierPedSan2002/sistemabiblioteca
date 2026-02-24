package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    /**
     * RF-01: Permite verificar si un correo ya existe antes de registrar.
     * El alcance exige que el correo sea único.
     */
    Optional<Usuarios> findByCorreoElectronico(String correoElectronico);

    /**
     * RF-03: Permite listar usuarios activos o inactivos.
     * Útil para validar si un usuario tiene permitido pedir préstamos.
     */
    List<Usuarios> findByEstado(Boolean estado);

    /**
     * Útil para filtrar por tipo (Estudiante, Profesor, Bibliotecario)
     * basándose en la nueva tabla de Roles del SQL.
     */
    List<Usuarios> findByIdRol(Integer idRol);
}