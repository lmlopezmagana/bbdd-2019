/**
 * 
 */
package com.salesianostriana.dam.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Curso;

/**
 * Repositorio que gestiona todos los cursos de nuestro sistema
 * @author luismi
 *
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {

	
}
