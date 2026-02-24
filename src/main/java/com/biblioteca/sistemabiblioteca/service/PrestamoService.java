package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Prestamo;
import com.biblioteca.sistemabiblioteca.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    public Prestamo registrarPrestamo(Prestamo prestamo) {
        // RF-09: REGLA DE ORO - Máximo 3 libros activos
        // Importante: Asegúrate que en PrestamoRepository el método se llame exactamente así
        long librosActivos = prestamoRepository.countByUsuarioIdAndEstado(prestamo.getUsuario().getId(), "Activo");
        
        if (librosActivos >= 3) {
            throw new RuntimeException("El usuario ya tiene 3 libros en su poder.");
        }

        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setEstado("Activo");
        return prestamoRepository.save(prestamo);
    }
}