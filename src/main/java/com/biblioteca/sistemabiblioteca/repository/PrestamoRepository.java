package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    /**
     * RF-11: Permite buscar préstamos por su estado actual.
     * Útil para los reportes de "Activos", "Vencidos" o "Devueltos".
     */
    List<Prestamo> findByEstado(String estado);
    
    /**
     * RF-09: REGLA DE ORO. 
     * Cuenta cuántos préstamos tiene un usuario que aún no han sido devueltos.
     * Se usa en el Service para bloquear el préstamo si el resultado es >= 3.
     */
    long countByUsuarioIdAndEstado(Long idUsuario, String estado);
}