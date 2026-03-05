package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

/**
 * REPOSITORIO DE GESTIÓN TRANSACCIONAL
 * Esta interfaz extiende JpaRepository para administrar la persistencia de los préstamos.
 * Es la pieza clave para el control de inventario y cumplimiento de políticas (RF-09 al RF-12).
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    /**
     * RF-11: FILTRADO POR ESTADO
     * Proporciona una lista de préstamos según su situación actual ('Activo', 'Devuelto', 'Moroso').
     */
    List<Prestamo> findByEstadoPrestamo(String estadoPrestamo);
    
    /**
     * RF-09: VALIDACIÓN DE LÍMITE DE PRÉSTAMOS
     * Cuenta los registros donde la fecha de devolución real es nula.
     * Si la fecha es null, significa que el usuario aún tiene el libro en su poder.
     * Esta es la validación técnica para la regla de máximo 3 libros.
     */
    long countByUsuarioIdAndFechaDevolucionRealIsNull(Long idUsuario);

    /**
     * BÚSQUEDA POR USUARIO
     * Permite obtener el historial completo de préstamos de un socio específico.
     */
    List<Prestamo> findByUsuarioId(Long idUsuario);
}