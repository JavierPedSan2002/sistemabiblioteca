package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import com.biblioteca.sistemabiblioteca.repository.PrestamoRepository;
import com.biblioteca.sistemabiblioteca.service.PrestamoService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CAPA DE CONTROLADOR PARA PRÉSTAMOS
 * Este componente gestiona el ciclo de vida de los préstamos, desde la salida 
 * del libro hasta su devolución (RF-09 al RF-11).
 */
@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*") // Habilita la interoperabilidad con clientes externos (JavaFX/Python).
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoService prestamoService; 

    /**
     * RF-09: REGISTRO DE PRÉSTAMO CON VALIDACIÓN
     * Procesa la solicitud de un préstamo. No se guarda directamente, sino que 
     * pasa por el 'Service' para validar reglas críticas (ej. límite de libros).
     */
    @PostMapping
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo) {
        // Delegamos la lógica al servicio para asegurar que el usuario esté habilitado.
        return prestamoService.registrarPrestamo(prestamo);
    }

    /**
     * RF-11: HISTORIAL Y LISTADO
     * Expone todos los registros de préstamos para auditoría y reportes.
     * Útil para que la herramienta de Python analice tendencias de lectura.
     */
    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    /**
     * RF-10: REGISTRO DE DEVOLUCIÓN
     * Actualiza el estado de un préstamo activo.
     * @param id Identificador único del préstamo a cerrar.
     * Captura la fecha y hora exacta de la devolución de forma automática.
     */
    @PutMapping("/{id}/devolver")
    public Prestamo devolverLibro(@PathVariable Long id) {
        // Buscamos el registro activo en la base de datos MySQL.
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        
        // Seteamos la fecha actual y cambiamos el estado para liberar el libro.
        prestamo.setFechaDevolucionReal(LocalDateTime.now());
        prestamo.setEstado("Devuelto");
        
        // Persistimos el cambio mediante el repositorio.
        return prestamoRepository.save(prestamo);
    }
}