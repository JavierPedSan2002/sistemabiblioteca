package com.biblioteca.sistemabiblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CLASE PRINCIPAL DEL SISTEMA (ENTRY POINT)
 * Esta es la clase que arranca todo el ecosistema de la aplicación.
 * Gracias a Spring Boot, permite ejecutar el sistema de forma autónoma sin 
 * necesidad de un servidor externo (como Tomcat) configurado manualmente.
 */
@SpringBootApplication
public class SistemabibliotecaApplication {

    /**
     * MÉTODO MAIN: El disparador del sistema.
     * Al ejecutarse, Spring Boot realiza tres acciones críticas:
     * 1. Auto-configuración: Configura el acceso a MySQL según tu properties.
     * 2. Component Scanning: Detecta automáticamente tus Controllers, Services y Repositorios.
     * 3. Levantamiento del servidor: Inicia el servidor web en el puerto 8081.
     */
    public static void main(String[] args) {
        SpringApplication.run(SistemabibliotecaApplication.class, args);
    }

}

