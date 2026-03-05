package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import com.biblioteca.sistemabiblioteca.model.Libro;
import com.biblioteca.sistemabiblioteca.repository.PrestamoRepository;
import com.biblioteca.sistemabiblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository; // Agregado para validar stock [cite: 54, 64]

    /**
     * RF-09: Registro de préstamos con validación de límite y stock.
     */
    @Transactional
    public Prestamo registrarPrestamo(Prestamo prestamo) {
        
        // 1. VALIDACIÓN: Máximo 3 libros activos por usuario 
        Long usuarioId = prestamo.getUsuario().getId();
        long prestamosActivos = prestamoRepository.countByUsuarioIdAndFechaDevolucionRealIsNull(usuarioId);
        
        if (prestamosActivos >= 3) {
            throw new RuntimeException("RESTRICCIÓN RF-09: El usuario ya tiene 3 libros activos.");
        }

        // 2. VALIDACIÓN: Disponibilidad de copias (Mínimo 3 por default) [cite: 49, 64]
        Libro libro = libroRepository.findById(prestamo.getLibro().getIsbn())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado en el catálogo."));

        if (libro.getCopiasDisponibles() <= 0) {
            throw new RuntimeException("RF-05: No hay copias disponibles de este libro.");
        }

        // 3. ACTUALIZACIÓN: Descontar una copia del inventario 
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() - 1);
        libroRepository.save(libro);

        // 4. GUARDAR: Registro del préstamo
        return prestamoRepository.save(prestamo);
    }
}