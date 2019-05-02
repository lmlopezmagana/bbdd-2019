/**
 * 
 */
package com.salesianostriana.dam.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;

/**
 * @author lmlopez
 *
 */
public interface AlumnoRepository 
	extends JpaRepository<Alumno, Long> {

	List<Alumno> findByCurso(Curso curso);
	
}
