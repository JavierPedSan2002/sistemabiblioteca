package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Define que esta clase es una API REST (RT-03)
@RequestMapping("/api/usuarios") // La URL base será http://localhost:8081/api/usuarios
public class UsuarioController {

    @Autowired // Inyecta el repositorio automáticamente (conecta las capas)
    private UsuarioRepository usuarioRepository;

    // RF-01: Registrar Usuario
    @PostMapping
    public Usuarios crearUsuario(@RequestBody Usuarios usuario) {
        return usuarioRepository.save(usuario); // Guarda el usuario en MySQL
    }

    // RF-02: Listar todos los usuarios
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll(); // Trae todos los registros
    }

    // RF-04: Eliminación Lógica (Cambiar estado a Inactivo)
    @PutMapping("/{id}/desactivar")
    public Usuarios desactivarUsuario(@PathVariable Long id) {
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setEstado("Inactivo"); // No borramos la fila, solo cambiamos el estado
        return usuarioRepository.save(usuario);
    }
}