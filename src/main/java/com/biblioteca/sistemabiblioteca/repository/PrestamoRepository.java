package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    /**
     * RF-11: FILTRADO POR ESTADO
     * Ajustado para coincidir con el atributo 'private String estado' de Prestamo.java
     */
    List<Prestamo> findByEstado(String estado);
    
    /**
     * RF-09: VALIDACIÓN DE LÍMITE DE 3 LIBROS
     * Cuenta préstamos no devueltos (fechaDevolucionReal es null).
     */
    long countByUsuarioIdAndFechaDevolucionRealIsNull(Long idUsuario);

    /**
     * HISTORIAL POR USUARIO
     */
    List<Prestamo> findByUsuarioId(Long idUsuario);
}