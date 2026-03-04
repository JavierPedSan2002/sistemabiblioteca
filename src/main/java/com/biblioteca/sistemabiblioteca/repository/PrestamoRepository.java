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
     * Este método es vital para alimentar los dashboards de control en tiempo real.
     */
    List<Prestamo> findByEstado(String estado);
    
    /**
     * RF-09: VALIDACIÓN DE LÍMITE DE PRÉSTAMOS
     * Implementa una consulta derivada que cuenta los registros activos de un usuario específico.
     * Es la base técnica para la regla de negocio que impide que un alumno retire más de 3 libros
     * simultáneamente, garantizando una distribución equitativa del acervo.
     */
    long countByUsuarioIdAndEstado(Long idUsuario, String estado);
}