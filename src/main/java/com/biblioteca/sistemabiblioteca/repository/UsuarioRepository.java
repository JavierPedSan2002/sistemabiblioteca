package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByCorreoElectronico(String correoElectronico);

    /**
     * RF-02: BÚSQUEDA POR NOMBRE COMPLETO
     * Ajustado para coincidir con el atributo 'nombreCompleto' de tu modelo.
     */
    List<Usuarios> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    List<Usuarios> findByEstado(Boolean estado);

    boolean existsByCorreoElectronico(String correoElectronico);

    List<Usuarios> findByIdRol(Integer idRol);
}