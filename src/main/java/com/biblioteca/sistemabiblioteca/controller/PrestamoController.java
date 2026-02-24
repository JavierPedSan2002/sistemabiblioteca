package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import com.biblioteca.sistemabiblioteca.repository.PrestamoRepository;
import com.biblioteca.sistemabiblioteca.service.PrestamoService; // Conexión añadida
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoService prestamoService; // Inyección añadida

    // RF-09: Registrar un préstamo (Con validación de límite)
    @PostMapping
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo) {
        return prestamoService.registrarPrestamo(prestamo);
    }

    // RF-11: Listar préstamos
    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    // RF-10: Registrar devolución
    @PutMapping("/{id}/devolver")
    public Prestamo devolverLibro(@PathVariable Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        
        prestamo.setFechaDevolucionReal(LocalDateTime.now());
        prestamo.setEstado("Devuelto");
        
        return prestamoRepository.save(prestamo);
    }
}