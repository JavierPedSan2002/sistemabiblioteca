package com.biblioteca.sistemabiblioteca.controller;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import com.biblioteca.sistemabiblioteca.service.UsuarioService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLADOR DE GESTIÓN DE USUARIOS
 * Centraliza las operaciones de administración de cuentas para alumnos y docentes (RF-01 al RF-04).
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Clave para permitir peticiones desde el frontend en JavaFX.
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService; 

    /**
     * RF-01: REGISTRO DE USUARIOS
     * Procesa la creación de nuevos usuarios delegando la validación de reglas 
     * (como correo único o formato de datos) al componente de Servicio.
     */
    @PostMapping
    public Usuarios crearUsuario(@RequestBody Usuarios usuario) {
        // El Service se encarga de que no se dupliquen correos y de inicializar el estado.
        return usuarioService.registrarUsuario(usuario);
    }

    /**
     * RF-02: CONSULTA GENERAL DE USUARIOS
     * Retorna el listado completo de usuarios registrados para su visualización en tablas o reportes.
     */
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * RF-04: ELIMINACIÓN LÓGICA (DESACTIVACIÓN)
     * En lugar de eliminar la fila de la base de datos, cambiamos el atributo 'estado'.
     * Esto mantiene la integridad referencial si el usuario tiene préstamos históricos.
     */
    @PutMapping("/{id}/desactivar")
    public Usuarios desactivarUsuario(@PathVariable Long id) {
        // Verificamos la existencia del usuario antes de proceder con el cambio de estado.
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no existe"));
        
        // Marcamos el estado como inactivo (false) para restringir sus privilegios sin borrar sus datos.
        usuario.setEstado(false); 
        return usuarioRepository.save(usuario);
    }
}