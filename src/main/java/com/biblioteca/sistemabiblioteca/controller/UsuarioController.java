package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import com.biblioteca.sistemabiblioteca.service.UsuarioService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLADOR DE GESTIÓN DE USUARIOS - VERSIÓN FINAL
 * Maneja el ciclo de vida de usuarios (RF-01 al RF-04 y actualizaciones).
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
     * RF-03: BÚSQUEDA POR ID
     */
    @GetMapping("/{id}")
    public Usuarios obtenerUsuario(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
    }

    /**
     * ACTUALIZACIÓN DE DATOS (NUEVO)
     * Permite editar nombre, correo, rol o estado.
     */
    @PutMapping("/{id}")
    public Usuarios actualizarUsuario(@PathVariable Integer id, @RequestBody Usuarios datosNuevos) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNombreCompleto(datosNuevos.getNombreCompleto());
                usuario.setCorreoElectronico(datosNuevos.getCorreoElectronico());
                usuario.setIdRol(datosNuevos.getIdRol());
                usuario.setEstado(datosNuevos.getEstado()); 
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
    }

    /**
     * RF-04: ELIMINACIÓN LÓGICA (DESACTIVACIÓN)
     */
    @PutMapping("/{id}/desactivar")
    public Usuarios desactivarUsuario(@PathVariable Integer id) {
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no existe"));
        
        usuario.setEstado(false); 
        return usuarioRepository.save(usuario);
    }

    /**
     * ELIMINACIÓN FÍSICA
     * Solo para registros sin historial de préstamos.
     */
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioRepository.deleteById(id);
    }
}