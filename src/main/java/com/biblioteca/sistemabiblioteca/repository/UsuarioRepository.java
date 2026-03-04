package com.biblioteca.sistemabiblioteca.repository;

import com.biblioteca.sistemabiblioteca.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * REPOSITORIO DE GESTIÓN DE USUARIOS
 * Esta interfaz abstrae la comunicación con la tabla 'usuarios' en MySQL.
 * Proporciona métodos de búsqueda personalizados para cumplir con la lógica
 * de seguridad y administración de perfiles (RF-01 al RF-04).
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    /**
     * RF-01: VALIDACIÓN DE IDENTIDAD ÚNICA
     * Busca un usuario por su correo electrónico. Se utiliza un 'Optional' para 
     * manejar de forma segura la posibilidad de que el correo no exista en la base de datos, 
     * evitando errores de puntero nulo (NullPointerException).
     */
    Optional<Usuarios> findByCorreoElectronico(String correoElectronico);

    /**
     * RF-03: FILTRADO POR DISPONIBILIDAD
     * Recupera una lista de usuarios basada en su estado lógico (Activo/Inactivo).
     * Es esencial para restringir operaciones de préstamo a usuarios que han sido 
     * dados de baja temporalmente.
     */
    List<Usuarios> findByEstado(Boolean estado);

    /**
     * GESTIÓN DE PRIVILEGIOS POR ROL
     * Filtra a los usuarios según su nivel de acceso (1=Estudiante, 2=Profesor, 3=Bibliotecario).
     * Permite al sistema segmentar la información y las funcionalidades que se muestran 
     * en la interfaz de usuario.
     */
    List<Usuarios> findByIdRol(Integer idRol);
}