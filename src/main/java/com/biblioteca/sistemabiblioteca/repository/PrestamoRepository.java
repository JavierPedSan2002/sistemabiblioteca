package com.biblioteca.sistemabiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.sistemabiblioteca.model.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{

}
