package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import com.biblioteca.sistemabiblioteca.repository.PrestamoRepository;
import com.biblioteca.sistemabiblioteca.service.PrestamoService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CAPA DE CONTROLADOR PARA PRÉSTAMOS - VERSIÓN FINAL
 * Gestiona el ciclo de vida completo: Registro, Devolución, Edición y Eliminación.
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
     */
    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    /**
     * RF-10: REGISTRO DE DEVOLUCIÓN (Acción específica)
     */
    @PutMapping("/{id}/devolver")
    public Prestamo devolverLibro(@PathVariable Integer id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        
        prestamo.setFechaDevolucionReal(LocalDateTime.now());
        prestamo.setEstadoPrestamo("Devuelto");
        
        return prestamoRepository.save(prestamo);
    }

    /**
     * ACTUALIZACIÓN GENERAL DE PRÉSTAMO
     * Útil para corregir fechas o estados manualmente.
     */
    @PutMapping("/{id}")
    public Prestamo actualizarPrestamo(@PathVariable Integer id, @RequestBody Prestamo datosNuevos) {
        return prestamoRepository.findById(id)
            .map(prestamo -> {
                prestamo.setFechaPrestamo(datosNuevos.getFechaPrestamo());
                prestamo.setFechaDevolucionEsperada(datosNuevos.getFechaDevolucionEsperada());
                prestamo.setEstadoPrestamo(datosNuevos.getEstadoPrestamo());
                // No solemos cambiar el libro o usuario de un préstamo, 
                // pero si es necesario, se agregarían aquí.
                return prestamoRepository.save(prestamo);
            })
            .orElseThrow(() -> new RuntimeException("No se encontró el préstamo con ID: " + id));
    }

    /**
     * ELIMINACIÓN DE PRÉSTAMO
     * Para borrar registros accidentales.
     */
    @DeleteMapping("/{id}")
    public void eliminarPrestamo(@PathVariable Integer id) {
        prestamoRepository.deleteById(id);
    }
}