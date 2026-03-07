package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * RF-01: Registro de usuarios con validación de correo único.
     * El estado se inicializa en true (activo).
     */
    @Transactional
    public Usuarios registrarUsuario(Usuarios usuario) {
        if(usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado en el sistema.");
        }
        
        // El campo fechaRegistro no se setea manualmente aquí porque en la BD 
        // de Ricardo tiene un DEFAULT CURRENT_TIMESTAMP.
        usuario.setEstado(true);
        return usuarioRepository.save(usuario);
    }

    /**
     * RF-03: Actualización sincronizada.
     * CAMBIO: Se usa Integer para el ID.
     */
    @Transactional
    public Usuarios actualizarUsuario(Integer id, Usuarios datosNuevos) {
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
     * CAMBIO: Se usa Integer para el ID.
     */
    @Transactional
    public void eliminarUsuario(Integer id) {
        Usuarios usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no existe."));
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
    }

    public List<Usuarios> listarTodos() {
        return usuarioRepository.findAll();
    }
}