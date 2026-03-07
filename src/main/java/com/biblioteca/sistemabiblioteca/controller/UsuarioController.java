package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import com.biblioteca.sistemabiblioteca.service.UsuarioService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLADOR DE GESTIÓN DE USUARIOS - ACTUALIZADO PARA biblioteca_universidad
 * Centraliza las operaciones de administración de cuentas (RF-01 al RF-04).
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService; 

    /**
     * RF-01: REGISTRO DE USUARIOS
     */
    @PostMapping
    public Usuarios crearUsuario(@RequestBody Usuarios usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    /**
     * RF-02: CONSULTA GENERAL DE USUARIOS
     */
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * RF-04: ELIMINACIÓN LÓGICA (DESACTIVACIÓN)
     * Actualizado: El ID ahora se maneja como Integer para ser compatible con el script SQL.
     */
    @PutMapping("/{id}/desactivar")
    public Usuarios desactivarUsuario(@PathVariable Integer id) { // CAMBIO: Long -> Integer
        // Verificamos la existencia usando la nueva llave primaria id_usuario (mapeada en el modelo)
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no existe"));
        
        // Cambio de estado para restricción de privilegios sin pérdida de datos históricos.
        usuario.setEstado(false); 
        return usuarioRepository.save(usuario);
    }
}