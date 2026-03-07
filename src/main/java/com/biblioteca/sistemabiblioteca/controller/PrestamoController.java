package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import com.biblioteca.sistemabiblioteca.repository.PrestamoRepository;
import com.biblioteca.sistemabiblioteca.service.PrestamoService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CAPA DE CONTROLADOR PARA PRÉSTAMOS - ACTUALIZADO PARA biblioteca_universidad
 * Gestiona el ciclo de vida de los préstamos (RF-09 al RF-11).
 */
@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*") 
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoService prestamoService; 

    /**
     * RF-09: REGISTRO DE PRÉSTAMO CON VALIDACIÓN
     */
    @PostMapping
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo) {
        return prestamoService.registrarPrestamo(prestamo);
    }

    /**
     * RF-11: HISTORIAL Y LISTADO
     * Expone datos para la herramienta de Python de Ricardo.
     */
    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    /**
     * RF-10: REGISTRO DE DEVOLUCIÓN
     * Se actualiza para coincidir con los estados definidos en el script SQL ('Devuelto').
     */
    @PutMapping("/{id}/devolver")
    public Prestamo devolverLibro(@PathVariable Integer id) { // Cambiado a Integer por el script SQL
        // Buscamos el registro mediante la PK id_prestamo
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        
        // Seteamos fecha real de devolución (TIMESTAMP en SQL)
        prestamo.setFechaDevolucionReal(LocalDateTime.now());
        
        // IMPORTANTE: El script de Ricardo usa 'estado_prestamo'. 
        // Asegúrate que en el modelo el setter se llame setEstadoPrestamo o esté mapeado correctamente.
        prestamo.setEstadoPrestamo("Devuelto");
        
        return prestamoRepository.save(prestamo);
    }
}