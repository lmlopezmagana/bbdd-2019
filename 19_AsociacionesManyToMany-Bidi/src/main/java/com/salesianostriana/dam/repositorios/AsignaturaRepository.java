/**
 * 
 */
package com.salesianostriana.dam.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Asignatura;

/**
 * Repositorio para manejar las instancias de Asignatura
 * 
 * @author luismi
 *
 */
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long>{

}
