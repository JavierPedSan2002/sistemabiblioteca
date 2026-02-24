package com.biblioteca.sistemabiblioteca.service;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import com.biblioteca.sistemabiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuarios registrarUsuario(Usuarios usuario) {
        // RF-01: Validar si el correo ya existe
        if(usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Sincronización con el SQL
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstado(true);
        
        return usuarioRepository.save(usuario);
    }
}