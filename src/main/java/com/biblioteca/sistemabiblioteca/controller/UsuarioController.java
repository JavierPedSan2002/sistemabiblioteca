package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import com.biblioteca.sistemabiblioteca.service.UsuarioService; // Importamos el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService; // Inyectamos el cerebro de la lógica

    // RF-01: Registrar Usuario usando la lógica del Service
    @PostMapping
    public Usuarios crearUsuario(@RequestBody Usuarios usuario) {
        // Llamamos al service para que valide el correo y asigne fecha/estado
        return usuarioService.registrarUsuario(usuario);
    }

    // RF-02: Listar todos los usuarios
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // RF-04: Eliminación Lógica (Desactivar)
    @PutMapping("/{id}/desactivar")
    public Usuarios desactivarUsuario(@PathVariable Long id) {
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no existe"));
        
        usuario.setEstado(false); // Cambiamos a false (Inactivo)
        return usuarioRepository.save(usuario);
    }
}