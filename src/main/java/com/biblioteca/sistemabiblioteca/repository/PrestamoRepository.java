package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> { // Cambiado a Integer
    
    /**
     * RF-11: FILTRADO POR ESTADO
     * Ajustado para 'estadoPrestamo' (nombre en el modelo nuevo)
     */
    List<Prestamo> findByEstadoPrestamo(String estadoPrestamo);
    
    /**
     * RF-09: VALIDACIÓN DE LÍMITE DE 3 LIBROS
     * Se ajusta para usar el objeto usuario y el ID como Integer.
     */
    long countByUsuarioIdAndFechaDevolucionRealIsNull(Integer idUsuario);

    /**
     * HISTORIAL POR USUARIO
     */
    List<Prestamo> findByUsuarioId(Integer idUsuario);
}