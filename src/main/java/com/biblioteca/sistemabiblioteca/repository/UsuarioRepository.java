package com.biblioteca.sistemabiblioteca.repository;

// IMPORTACIONES: Traemos las herramientas necesarias
import com.biblioteca.sistemabiblioteca.model.Usuarios; // Importa tu entidad para saber qué tabla manejar
import org.springframework.data.jpa.repository.JpaRepository; // La herramienta que hace el trabajo sucio de SQL
import org.springframework.stereotype.Repository; // Etiqueta que identifica esta clase ante Spring
import java.util.Optional; // Contenedor para evitar errores si algo no existe

/**
 * @Repository: Le dice a Spring que esta interfaz es un componente de persistencia.
 * Automáticamente se encarga de traducir los datos de Java a MySQL y viceversa.
 */
@Repository

    /**
     * extends JpaRepository<Usuarios, Long>:
     * 1. 'Usuarios': Indica que este repositorio es para la tabla de usuarios.
     * 2. 'Long': Indica que el ID (@Id) de esa tabla es de tipo numérico largo.
     * * Al heredar de JpaRepository, ya tienes listos métodos como:
     * .findAll() -> SELECT * FROM usuarios; (Para el RF-02)
     * .save()    -> INSERT o UPDATE usuarios; (Para el RF-01 y RF-03)
     * .findById() -> SELECT * FROM usuarios WHERE id = ?; (Para el RF-02)
     */
    
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    
    /**
     * findByCorreoElectronico:
     * Esto es un "Query Method". Spring lee el nombre del método y construye el SQL:
     * "SELECT * FROM usuarios WHERE correo_electronico = ?"
     * * Optional<Usuarios>: Se usa porque el correo podría no existir en la DB. 
     * Si no existe, devuelve un Optional vacío en lugar de un error "Null", 
     * haciendo el sistema más estable. (Cumple con RF-02)
     */
    Optional<Usuarios> findByCorreoElectronico(String correoElectronico);
}