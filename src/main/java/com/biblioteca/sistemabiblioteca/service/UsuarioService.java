package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * RF-01: Registro de usuarios con validación de correo único.
     */
    @Transactional
    public Usuarios registrarUsuario(Usuarios usuario) {
        if(usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado en el sistema.");
        }
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstado(true);
        return usuarioRepository.save(usuario);
    }

    /**
     * RF-03: Actualización sincronizada con el modelo Usuarios.java
     */
    @Transactional
    public Usuarios actualizarUsuario(Long id, Usuarios datosNuevos) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNombreCompleto(datosNuevos.getNombreCompleto());
                usuario.setPasswordHash(datosNuevos.getPasswordHash());
                usuario.setIdRol(datosNuevos.getIdRol());
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    /**
     * RF-04: Baja lógica del usuario.
     */
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuarios usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no existe."));
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
    }

    public List<Usuarios> listarTodos() {
        return usuarioRepository.findAll();
    }
}