/**
 * 
 */
package com.salesianostriana.dam.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Curso;

/**
 * Repositorio para la entidad Curso
 * @author luismi
 *
 */
public interface CursoRepository 
		extends JpaRepository<Curso, Long> {

}
